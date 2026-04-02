package org.cloudburstmc.protocol.bedrock.data.inventory;

/**
 * Enumerates inventory tab left values used in the Bedrock protocol.
 */
public enum InventoryTabLeft {
    NONE,
    RECIPE_CONSTRUCTION,
    RECIPE_EQUIPMENT,
    RECIPE_ITEMS,
    RECIPE_NATURE,
    RECIPE_SEARCH,
    SURVIVAL;

    public static final InventoryTabLeft[] VALUES = values();
}
