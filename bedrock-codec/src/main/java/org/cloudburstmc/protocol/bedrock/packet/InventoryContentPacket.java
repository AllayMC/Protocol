package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

import java.util.List;

/**
 * Sent by the server to update the full content of a particular inventory. It is usually sent for
 * the main inventory of the player, but also works for other inventories that are currently opened
 * by the player.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class InventoryContentPacket implements BedrockPacket {
    /**
     * The full updated contents of the inventory. Its size should match the size of the target
     * inventory window.
     */
    private List<ItemData> contents = new ObjectArrayList<>();
    /**
     * The ID of the inventory window being updated.
     */
    private int containerId;
    /**
     * @since v712
     */
    private FullContainerName containerNameData =
            new FullContainerName(ContainerSlotType.ANVIL_INPUT, null);
    /**
     * The size of the dynamic container.
     *
     * @since v729
     * @deprecated since v748. Use the storage item stack size instead.
     */
    @Deprecated
    private int dynamicContainerSize;
    /**
     * The item that is acting as the storage container for the inventory. If the inventory is not a
     * dynamic container then this field should be left empty. When set, only the item type is used
     * by the client and none of the other stack info.
     *
     * @since v748
     */
    private ItemData storageItem = ItemData.AIR;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.INVENTORY_CONTENT;
    }

    @Override
    public InventoryContentPacket clone() {
        try {
            return (InventoryContentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
