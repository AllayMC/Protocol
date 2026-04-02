package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client when it tries to toggle the state of a slot within a Crafter.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ToggleCrafterSlotRequestPacket implements BedrockPacket {
    /**
     * The block position of the Crafter whose slot is being toggled.
     */
    private Vector3i blockPosition;
    /**
     * The index of the slot that was toggled. This should be a value between 0 and 8.
     */
    private byte slot;
    /**
     * The new state of the slot. If true, the slot is disabled, if false, the slot is enabled.
     */
    private boolean disabled;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TOGGLE_CRAFTER_SLOT_REQUEST;
    }

    @Override
    public ToggleCrafterSlotRequestPacket clone() {
        try {
            return (ToggleCrafterSlotRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
