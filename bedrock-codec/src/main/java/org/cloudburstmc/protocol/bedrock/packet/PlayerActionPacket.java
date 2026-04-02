package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.PlayerActionType;

/**
 * Sent by the client when it executes any action, for example starting to sprint, swim, starting
 * the breaking of a block, dropping an item, etc.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerActionPacket implements BedrockPacket {
    /**
     * The runtime ID of the player. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * The ID of the action that was executed by the player. It is one of the constants that may be
     * found in protocol/player.go.
     */
    private PlayerActionType action;
    /**
     * The position of the target block, if the action with the ActionType set concerned a block. If
     * that is not the case, the block position will be zero.
     */
    private Vector3i blockPosition;
    /**
     * The face of the target block that was interacted with. This is only meaningful for
     * block-related actions.
     */
    private int face;
    /**
     * The position of the action's result. When a UseItemOn action is sent, this is the position of
     * the block clicked, but when a block is placed, this is the position at which the block will
     * be placed.
     *
     * @since v527
     */
    private Vector3i resultPosition;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_ACTION;
    }

    @Override
    public PlayerActionPacket clone() {
        try {
            return (PlayerActionPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
