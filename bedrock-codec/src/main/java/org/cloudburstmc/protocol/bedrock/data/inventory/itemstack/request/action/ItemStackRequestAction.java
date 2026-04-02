package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * ItemStackRequest is sent by the client to change item stacks in an inventory. It is essentially a
 * replacement of the InventoryTransaction packet added in 1.16 for inventory specific actions, such
 * as moving items around or crafting. The InventoryTransaction packet is still used for actions
 * such as placing blocks and interacting with entities.
 */
public interface ItemStackRequestAction {

    ItemStackRequestActionType getType();
}
