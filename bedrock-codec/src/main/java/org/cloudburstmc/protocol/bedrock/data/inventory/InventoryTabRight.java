package org.cloudburstmc.protocol.bedrock.data.inventory;

/**
 * Enumerates inventory tab right values used in the Bedrock protocol.
 */
public enum InventoryTabRight {
    NONE,
    FULL_SCREEN,
    CRAFTING,
    ARMOR;

    public static final InventoryTabRight[] VALUES = values();
}
