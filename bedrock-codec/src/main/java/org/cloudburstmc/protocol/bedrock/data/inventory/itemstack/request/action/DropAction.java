package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;

/**
 * DropStackRequestActionData is sent by the client when it drops an item out of the inventory when it has its
 * inventory opened. This action is not sent when a player drops an item out of the hotbar using the Q button
 * (or the equivalent on mobile). The InventoryTransaction packet is still used for that action, regardless of
 * whether the item stack network IDs are used or not.
 *
 * @param count    The count.
 * @param source   The source.
 * @param randomly ?? Perhaps deals with order of items being dropped? Normally false.
 */
public record DropAction(int count, ItemStackRequestSlotData source, boolean randomly) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.DROP;
    }
}
