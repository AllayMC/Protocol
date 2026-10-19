package org.cloudburstmc.protocol.bedrock.data;


/**
 * Furnace screen selection, filtering, and layout preferences.
 *
 * @param leftTabIndex the selected recipe tab
 * @param filtering   whether the recipe filter is enabled
 * @param layout      the screen layout
 * @since v2192
 */
public record FurnaceOptions(FurnaceLeftTabIndex leftTabIndex, boolean filtering, FurnaceLayout layout) {

    /**
     * Recipe tabs in the furnace screen.
     *
     * @since v2192
     */
    public enum FurnaceLeftTabIndex {
        NONE,
        RECIPE_FOOD,
        RECIPE_ITEMS,
        RECIPE_BLOCKS,
        RECIPE_SEARCH,
        INVENTORY
    }

    /**
     * Furnace screen layouts.
     *
     * @since v2192
     */
    public enum FurnaceLayout {
        NONE,
        INVENTORY_ONLY,
        DEFAULT
    }
}
