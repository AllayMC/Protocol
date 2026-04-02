package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;

/**
 * Sent by the client when using pick block so the server can place the matching item into the
 * player's inventory.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class BlockPickRequestPacket implements BedrockPacket {
    /**
     * Position of the block the client wants to pick into {@link #hotbarSlot}.
     */
    private Vector3i blockPosition;
    /**
     * Whether the picked item should include the block entity NBT so placing it recreates the
     * picked block more faithfully.
     */
    private boolean addUserData;
    /**
     * Hotbar slot that was selected when the pick request was issued.
     */
    private int hotbarSlot;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BLOCK_PICK_REQUEST;
    }

    @Override
    public BlockPickRequestPacket clone() {
        try {
            return (BlockPickRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
