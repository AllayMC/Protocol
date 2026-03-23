package org.cloudburstmc.protocol.bedrock.data.inventory.crafting;

import lombok.Value;

/**
 * Represents a potion mixing recipe which may be used in a brewing stand.
 */
@Value
public class PotionMixData {
    // Potion to be put in
    private final int inputId;

    /**
     * @since v407
     */
    private final int inputMeta;

    // Item to be added to the brewing stand to brew the output potion
    private final int reagentId;

    /**
     * @since v407
     */
    private final int reagentMeta;

    // Output Potion
    private final int outputId;

    /**
     * @since v407
     */
    private final int outputMeta;
}
