package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

/**
 * Represents the legacy method of transforming biomes.
 *
 * @param weightedBiomes      A list of biomes and their weights.
 * @param conditionJson       The condition json.
 * @param minPassingNeighbors MinPassingNeighbours is the minimum number of neighbours that must
 *                            pass the condition for the transformation to be applied.
 */
public record BiomeConditionalTransformationData(List<BiomeWeightedData> weightedBiomes, String conditionJson,
                                                 long minPassingNeighbors) {

    @JsonCreator
    public BiomeConditionalTransformationData {
    }
}
