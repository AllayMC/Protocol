package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;

/**
 * TakeStackRequestActionData is sent by the client to the server to take x amount of items from one slot in a
 * container to the cursor.
 *
 * @param count       The count.
 * @param source      The source.
 * @param destination The destination.
 */
public record TakeAction(int count, ItemStackRequestSlotData source, ItemStackRequestSlotData destination) implements TransferItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.TAKE;
    }
}
