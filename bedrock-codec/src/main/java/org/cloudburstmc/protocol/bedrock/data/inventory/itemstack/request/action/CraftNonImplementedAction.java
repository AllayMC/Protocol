package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * CraftNonImplementedStackRequestActionData is an action sent for inventory actions that aren't yet implemented
 * in the new system. These include, for example, anvils
 */
public record CraftNonImplementedAction() implements ItemStackRequestAction {

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_NON_IMPLEMENTED_DEPRECATED;
    }
}
