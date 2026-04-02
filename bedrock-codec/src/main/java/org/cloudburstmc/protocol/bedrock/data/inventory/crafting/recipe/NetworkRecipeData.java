package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

/**
 * FurnaceDataRecipe is a recipe specifically used for furnace-type crafting stations. It is equal
 * to FurnaceRecipe, except it has an input item with a specific metadata value, instead of any
 * metadata value.
 */
public interface NetworkRecipeData extends RecipeData {

    int getNetId();
}
