package org.cloudburstmc.protocol.bedrock.data.inventory.crafting;

/**
 * ContainerSetData is sent by the server to update specific data of a single container, meaning a
 * block such as a furnace or a brewing stand. This data is usually used by the client to display
 * certain features client-side.
 *
 * @param inputId   The input ID.
 * @param reagentId The reagent ID.
 * @param outputId  The output ID.
 */
public record ContainerMixData(int inputId, int reagentId, int outputId) {
}
