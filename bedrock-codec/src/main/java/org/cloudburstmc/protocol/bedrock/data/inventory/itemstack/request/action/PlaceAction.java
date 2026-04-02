package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;

/**
 * PlaceStackRequestAction is sent by the client to the server to place x amount of items from one slot into
 * another slot, such as when shift clicking an item in the inventory to move it around or when moving an item
 * in the cursor into a slot.
 *
 * @param count       The count.
 * @param source      The source.
 * @param destination The destination.
 */
public record PlaceAction(int count, ItemStackRequestSlotData source,
                          ItemStackRequestSlotData destination) implements TransferItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.PLACE;
    }
}
