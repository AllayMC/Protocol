package org.cloudburstmc.protocol.bedrock.data.inventory.crafting;

/**
 * CraftingData is sent by the server to let the client know all crafting data that the server
 * maintains. This includes shapeless crafting, crafting table recipes, furnace recipes etc. Each
 * crafting station's recipes are included in it.
 */
public enum CraftingDataType {
    SHAPELESS,
    SHAPED,
    FURNACE,
    FURNACE_DATA,
    MULTI,
    SHULKER_BOX, // USER_DATA_SHAPELESS
    SHAPELESS_CHEMISTRY,
    SHAPED_CHEMISTRY,
    /**
     * @since v567
     */
    SMITHING_TRANSFORM,
    /**
     * @since v582
     */
    SMITHING_TRIM;

    private static final CraftingDataType[] VALUES = values();

    public static CraftingDataType byId(int id) {
        if (id >= 0 && id < VALUES.length) {
            return VALUES[id];
        }
        throw new UnsupportedOperationException("Unknown CraftingDataType ID: " + id);
    }
}
