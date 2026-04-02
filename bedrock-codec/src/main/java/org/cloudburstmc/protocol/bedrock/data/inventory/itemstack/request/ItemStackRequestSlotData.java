package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request;

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;

/**
 * Holds information on a specific slot client-side.
 *
 * @param container      container the slot was in
 * @param slot           slot is the index of the slot within the container
 * @param stackNetworkId stackNetworkId is the unique stack ID that the client assumes to be present in this slot. The server
 *                       must check if these IDs match. If they do not match, servers should reject the stack request that the
 *                       action holding this info was in.
 * @param containerName  The container name.
 */
public record ItemStackRequestSlotData(ContainerSlotType container, int slot, int stackNetworkId, FullContainerName containerName) {
}
