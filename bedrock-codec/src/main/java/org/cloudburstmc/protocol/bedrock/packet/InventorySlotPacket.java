package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * Sent by the server to update a single slot in one of the inventory windows that the client
 * currently has opened. Usually this is the main inventory, but it may also be the off hand or, for
 * example, a chest inventory.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class InventorySlotPacket implements BedrockPacket {
    /**
     * The ID of the inventory window being updated.
     */
    private int containerId;
    /**
     * The index of the slot that the packet modifies. The new item will be set to the slot at this
     * index.
     */
    private int slot;
    /**
     * The item to place into the target slot.
     */
    private ItemData item;
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
        return BedrockPacketType.INVENTORY_SLOT;
    }

    @Override
    public InventorySlotPacket clone() {
        try {
            return (InventorySlotPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
