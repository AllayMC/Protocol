package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;

/**
 * ConsumeStackRequestAction is sent by the client when it uses an item to craft another item. The original
 * item is 'consumed'.
 *
 * @param count  The count.
 * @param source The source.
 */
public record ConsumeAction(int count, ItemStackRequestSlotData source) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CONSUME;
    }
}
