package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * Represents craft grindstone action used in the Bedrock protocol.
 *
 * @param recipeNetworkId         The recipe network ID.
 * @param numberOfRequestedCrafts The number of requested crafts.
 * @param repairCost              The repair cost.
 */
public record CraftGrindstoneAction(int recipeNetworkId, int numberOfRequestedCrafts, int repairCost) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_REPAIR_AND_DISENCHANT;
    }
}
