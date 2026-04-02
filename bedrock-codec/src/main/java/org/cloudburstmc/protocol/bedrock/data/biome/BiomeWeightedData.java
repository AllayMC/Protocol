package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * BiomeElementData are set rules to adjust the surface materials of the biome.
 *
 * @param biome  The biome.
 * @param weight The weight.
 */
public record BiomeWeightedData(String biome, int weight) {

    @JsonCreator
    public BiomeWeightedData {
    }
}
