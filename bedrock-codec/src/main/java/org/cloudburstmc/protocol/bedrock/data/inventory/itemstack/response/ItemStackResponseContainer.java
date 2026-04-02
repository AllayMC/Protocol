package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response;

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;

import java.util.List;

/**
 * ContainerEntry holds information on what slots in a container have what item stack in them.
 *
 * @param container     container that the slots that follow are in.
 * @param items         items holds information on what item stack should be present in specific slots in the container.
 * @param containerName The container name.
 */
public record ItemStackResponseContainer(ContainerSlotType container, List<ItemStackResponseSlot> items, FullContainerName containerName) {
}
