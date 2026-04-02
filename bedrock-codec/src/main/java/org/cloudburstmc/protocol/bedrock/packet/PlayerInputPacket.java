package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to the server when the player is moving but the server does not allow it to
 * update its movement using the MovePlayer packet. It includes situations where the player is
 * riding an entity like a boat. If this is the case, the packet is sent roughly every tick.
 *
 * @deprecated Removed as of v800 (1.21.80). Server authoritative input is handled by {@link
 * PlayerAuthInputPacket}
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class PlayerInputPacket implements BedrockPacket {
    /**
     * The movement vector produced from directional input.
     */
    private Vector2f inputMotion;
    /**
     * Whether the player is currently jumping.
     */
    private boolean jumping;
    /**
     * Whether the player is currently sneaking.
     */
    private boolean sneaking;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_INPUT;
    }

    @Override
    public PlayerInputPacket clone() {
        try {
            return (PlayerInputPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
