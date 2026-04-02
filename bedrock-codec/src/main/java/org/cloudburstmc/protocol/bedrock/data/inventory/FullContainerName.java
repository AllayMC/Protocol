package org.cloudburstmc.protocol.bedrock.data.inventory;

/**
 * FullContainerName contains information required to identify a container in a
 * StackRequestSlotInfo.
 *
 * @param container The container.
 * @param dynamicId May be null if not present since v729
 */
public record FullContainerName(ContainerSlotType container, Integer dynamicId) {
}
