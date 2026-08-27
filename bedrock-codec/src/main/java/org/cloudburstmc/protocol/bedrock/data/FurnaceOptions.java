package org.cloudburstmc.protocol.bedrock.data;


public record FurnaceOptions(FurnaceLeftTabIndex leftTabIndex, boolean filtering, FurnaceLayout layout) {

    public enum FurnaceLeftTabIndex {
        NONE,
        RECIPE_FOOD,
        RECIPE_ITEMS,
        RECIPE_BLOCKS,
        RECIPE_SEARCH,
        INVENTORY
    }

    public enum FurnaceLayout {
        NONE,
        INVENTORY_ONLY,
        DEFAULT
    }
}
