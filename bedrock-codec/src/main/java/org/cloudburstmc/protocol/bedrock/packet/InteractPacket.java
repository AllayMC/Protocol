package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client when it performs one of the remaining entity interaction actions that are not
 * covered by the inventory transaction packets.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class InteractPacket implements BedrockPacket {
    /**
     * The interaction action performed by the client.
     */
    private Action action;
    /**
     * The runtime entity id of the interacted entity. This is {@code 0} for
     * {@link Action#OPEN_INVENTORY}.
     */
    private long runtimeEntityId;
    /**
     * The position associated with {@link #action}. For {@link Action#MOUSEOVER} this is the
     * cursor position on the entity, and for {@link Action#LEAVE_VEHICLE} it is the position where
     * the client expects the player to appear after dismounting.
     */
    private Vector3f mousePosition;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.INTERACT;
    }

    public enum Action {
        NONE,
        INTERACT,
        DAMAGE,
        LEAVE_VEHICLE,
        MOUSEOVER,
        NPC_OPEN,
        OPEN_INVENTORY
    }

    @Override
    public InteractPacket clone() {
        try {
            return (InteractPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
