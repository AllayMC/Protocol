package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;

/**
 * DestroyStackRequestActionData is sent by the client when it destroys an item in creative mode by moving it
 * back into the creative inventory.
 *
 * @param count  The count.
 * @param source The source.
 */
public record DestroyAction(int count, ItemStackRequestSlotData source) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.DESTROY;
    }
}
