package org.cloudburstmc.protocol.bedrock.data.inventory.transaction;

/**
 * InventoryTransactionData represents an object that holds data specific to an inventory
 * transaction type. The data it holds depends on the type.
 */
public enum InventoryTransactionType {
    NORMAL,
    INVENTORY_MISMATCH,
    ITEM_USE,
    ITEM_USE_ON_ENTITY,
    ITEM_RELEASE
}
