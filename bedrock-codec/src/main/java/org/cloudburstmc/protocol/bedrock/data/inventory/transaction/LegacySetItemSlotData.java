package org.cloudburstmc.protocol.bedrock.data.inventory.transaction;

/**
 * Legacy slot update information attached to an inventory transaction.
 *
 * @param containerId The container ID.
 * @param slots       The slot indices within that container.
 */
public record LegacySetItemSlotData(int containerId, byte[] slots) {
}
