package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.CreativeItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.CreativeItemGroup;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to define the contents and grouping of the client's creative inventory.
 * Introduced in 1.16, it replaced sending {@link InventoryContentPacket} for the creative window.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CreativeContentPacket implements BedrockPacket {
    /**
     * The creative items to show in the inventory.
     */
    private final List<CreativeItemData> contents = new ObjectArrayList<>();
    /**
     * The creative inventory groups referenced by {@link #contents}.
     *
     * @since v776
     */
    private final List<CreativeItemGroup> groups = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CREATIVE_CONTENT;
    }

    @Override
    public CreativeContentPacket clone() {
        try {
            return (CreativeContentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
