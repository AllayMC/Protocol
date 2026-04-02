package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * Deprecated item stack request action sent by the client after crafting or enchanting. It carries
 * the resulting items and the number of times the recipe was crafted.
 *
 * @param resultItems  The resulting items reported by the client.
 * @param timesCrafted The number of times the recipe was crafted.
 */
public record CraftResultsDeprecatedAction(ItemData[] resultItems, int timesCrafted) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_RESULTS_DEPRECATED;
    }
}
