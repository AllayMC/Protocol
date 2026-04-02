package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * ItemStackRequest is sent by the client to change item stacks in an inventory. It is essentially a
 * replacement of the InventoryTransaction packet added in 1.16 for inventory specific actions, such
 * as moving items around or crafting. The InventoryTransaction packet is still used for actions
 * such as placing blocks and interacting with entities.
 */
public enum ItemStackRequestActionType {
    TAKE,
    PLACE,
    SWAP,
    DROP,
    DESTROY,
    CONSUME,
    CREATE,
    LAB_TABLE_COMBINE,
    BEACON_PAYMENT,
    MINE_BLOCK,
    CRAFT_RECIPE,
    CRAFT_RECIPE_AUTO,
    CRAFT_CREATIVE,
    CRAFT_RECIPE_OPTIONAL,
    CRAFT_REPAIR_AND_DISENCHANT,
    CRAFT_LOOM,
    CRAFT_NON_IMPLEMENTED_DEPRECATED,
    CRAFT_RESULTS_DEPRECATED,
    /**
     * @deprecated since 712
     */
    @Deprecated
    PLACE_IN_ITEM_CONTAINER,
    /**
     * @deprecated since 712
     */
    @Deprecated
    TAKE_FROM_ITEM_CONTAINER
}
