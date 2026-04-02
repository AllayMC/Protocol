package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * CraftCreativeStackRequestActionData is sent by the client when it takes an item out of the creative inventory.
 * The item is thus not really crafted, but instantly created.
 *
 * @param creativeItemNetworkId   creativeItemNetworkId is the network ID of the creative item that is being created. This is one of the
 *                                creative item network IDs sent in the CreativeContent packet.
 * @param numberOfRequestedCrafts NumberOfCrafts is how many times the recipe was crafted. This field appears to be boilerplate
 *                                and has no effect.
 */
public record CraftCreativeAction(int creativeItemNetworkId, int numberOfRequestedCrafts) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_CREATIVE;
    }
}
