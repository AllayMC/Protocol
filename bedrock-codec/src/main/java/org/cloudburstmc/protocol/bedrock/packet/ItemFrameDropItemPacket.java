package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client when it removes the displayed item from an item frame.
 *
 * @deprecated since v662
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class ItemFrameDropItemPacket implements BedrockPacket {
    /**
     * The block position of the item frame.
     */
    private Vector3i blockPosition;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ITEM_FRAME_DROP_ITEM;
    }

    @Override
    public ItemFrameDropItemPacket clone() {
        try {
            return (ItemFrameDropItemPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
