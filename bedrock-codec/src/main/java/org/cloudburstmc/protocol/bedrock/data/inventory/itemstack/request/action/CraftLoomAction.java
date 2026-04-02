package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * Represents craft loom action used in the Bedrock protocol.
 *
 * @param patternId    The pattern ID.
 * @param timesCrafted The times crafted.
 */
public record CraftLoomAction(String patternId, int timesCrafted) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_LOOM;
    }
}
