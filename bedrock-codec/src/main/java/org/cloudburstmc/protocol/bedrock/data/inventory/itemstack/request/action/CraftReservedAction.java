package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;


public record CraftReservedAction(String reservedId, int numCrafts) implements ItemStackRequestAction {

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.RESERVED;
    }
}
