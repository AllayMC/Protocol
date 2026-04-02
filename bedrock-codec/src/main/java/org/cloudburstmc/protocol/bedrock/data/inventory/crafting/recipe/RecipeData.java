package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;

/**
 * Represents a recipe that may be sent in a CraftingData packet to let the client know what recipes
 * are available server-side.
 */
public interface RecipeData {

    CraftingDataType getType();
}
