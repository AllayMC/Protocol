package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;


/**
 * A crafting request for the reserved action slot.
 * The action is registered in the item stack request type map from v2225.
 *
 * @param reservedId the reserved recipe identifier
 * @param numCrafts  the requested craft count
 * @since v2225
 */
public record CraftReservedAction(String reservedId, int numCrafts) implements ItemStackRequestAction {

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.RESERVED;
    }
}
