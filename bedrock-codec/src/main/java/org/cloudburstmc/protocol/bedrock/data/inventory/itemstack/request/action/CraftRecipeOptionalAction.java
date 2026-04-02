package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * Called when renaming an item in an anvil or cartography table. Uses the filter strings present in the request.
 *
 * @param recipeNetworkId     For the cartography table, if a certain MULTI recipe is being called, this points to the network ID that was assigned.
 * @param filteredStringIndex Most likely the index in the request's filter strings that this action is using
 */
public record CraftRecipeOptionalAction(int recipeNetworkId, int filteredStringIndex) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_RECIPE_OPTIONAL;
    }
}
