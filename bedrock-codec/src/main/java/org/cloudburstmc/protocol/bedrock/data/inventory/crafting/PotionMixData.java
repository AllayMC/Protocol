package org.cloudburstmc.protocol.bedrock.data.inventory.crafting;

/**
 * Represents a potion mixing recipe which may be used in a brewing stand.
 *
 * @param inputId     The input ID.
 * @param inputMeta   The input meta.
 * @param reagentId   The reagent ID.
 * @param reagentMeta The reagent meta.
 * @param outputId    The output ID.
 * @param outputMeta  The output meta.
 */
public record PotionMixData(int inputId, int inputMeta, int reagentId, int reagentMeta, int outputId, int outputMeta) {
    // Potion to be put in
    // Item to be added to the brewing stand to brew the output potion
    // Output Potion
}
