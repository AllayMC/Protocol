package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * CraftRecipeStackRequestActionData is sent by the client the moment it begins crafting an item. This is the
 * first action sent, before the Consume and Create item stack request actions.
 * This action is also sent when an item is enchanted. Enchanting should be treated mostly the same way as
 * crafting, where the old item is consumed.
 *
 * @param recipeNetworkId         The network ID of the recipe that is about to be crafted. This network ID matches one of the
 *                                recipes sent in the CraftingData packet, where each of the recipes have a RecipeNetworkID as
 *                                of 1.16.
 * @param numberOfRequestedCrafts NumberOfCrafts is how many times the recipe was crafted. This field appears to be boilerplate
 *                                and has no effect.
 */
public record CraftRecipeAction(int recipeNetworkId, int numberOfRequestedCrafts) implements RecipeItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_RECIPE;
    }
}
