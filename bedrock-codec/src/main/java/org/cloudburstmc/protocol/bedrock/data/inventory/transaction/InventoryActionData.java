package org.cloudburstmc.protocol.bedrock.data.inventory.transaction;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * Represents a single action that took place during an inventory transaction. On itself, this
 * inventory action is always unbalanced: It must be combined with other actions in an inventory
 * transaction to form a balanced transaction.
 *
 * @param source         The source.
 * @param slot           The slot.
 * @param fromItem       The from item.
 * @param toItem         The to item.
 * @param stackNetworkId The stack network ID.
 */
public record InventoryActionData(InventorySource source, int slot, ItemData fromItem, ItemData toItem, int stackNetworkId) {
    public InventoryActionData(InventorySource source, int slot, ItemData fromItem, ItemData toItem) {
        this(source, slot, fromItem, toItem, 0);
    }

    public InventoryActionData reverse() {
        return new InventoryActionData(source, slot, toItem, fromItem, stackNetworkId);
    }
}
