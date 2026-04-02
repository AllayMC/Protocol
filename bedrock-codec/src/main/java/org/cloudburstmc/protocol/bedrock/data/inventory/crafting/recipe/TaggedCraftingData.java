package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

/**
 * CraftingData is sent by the server to let the client know all crafting data that the server
 * maintains. This includes shapeless crafting, crafting table recipes, furnace recipes etc. Each
 * crafting station's recipes are included in it.
 */
public interface TaggedCraftingData extends RecipeData {

    String getTag();
}
