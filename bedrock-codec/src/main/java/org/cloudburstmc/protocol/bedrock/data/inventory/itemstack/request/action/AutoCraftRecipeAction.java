package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

import java.util.List;

/**
 * AutoCraftRecipeStackRequestActionData is sent by the client similarly to the CraftRecipeStackRequestActionData. The
 * only difference is that the recipe is automatically created and crafted by shift clicking the recipe book.
 *
 * @param recipeNetworkId         The network ID of the recipe that is about to be crafted. This network ID matches one of the
 *                                recipes sent in the CraftingData packet, where each of the recipes have a RecipeNetworkID as
 *                                of 1.16.
 * @param timesCrafted            The how many times the recipe was crafted.
 * @param ingredients             A slice of ItemDescriptorCount that contains the ingredients that were used to craft the
 *                                recipe. It is not exactly clear what this is used for, but it is sent by the vanilla client.
 * @param numberOfRequestedCrafts NumberOfCrafts is how many times the recipe was crafted. This field is just a duplicate of
 *                                TimesCrafted.
 */
public record AutoCraftRecipeAction(int recipeNetworkId, int timesCrafted, List<ItemDescriptorWithCount> ingredients,
                                    int numberOfRequestedCrafts) implements RecipeItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_RECIPE_AUTO;
    }
}
