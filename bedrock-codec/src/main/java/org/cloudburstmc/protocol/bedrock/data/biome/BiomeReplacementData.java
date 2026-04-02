package org.cloudburstmc.protocol.bedrock.data.biome;

import java.util.List;

/**
 * Represents data for biome replacements.
 *
 * @param biome               The biome ID to replace.
 * @param dimension           The dimension ID where the replacement applies.
 * @param targetBiomes        A list of target biome IDs for the replacement.
 * @param amount              The amount of replacement to apply.
 * @param noiseFrequencyScale The noise frequency scale.
 * @param replacementIndex    The index of the replacement.
 */
public record BiomeReplacementData(int biome, int dimension, List<Short> targetBiomes, float amount, float noiseFrequencyScale,
                                   int replacementIndex) {

}
