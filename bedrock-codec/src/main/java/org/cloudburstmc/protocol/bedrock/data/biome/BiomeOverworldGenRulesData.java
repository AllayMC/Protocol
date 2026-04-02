package org.cloudburstmc.protocol.bedrock.data.biome;

import java.util.List;

/**
 * BiomeOverworldRules specifies a list of transformation rules to apply to different parts of the
 * overworld.
 *
 * @param hillsTransformations        A list of weighted biome transformations to apply to hills.
 * @param mutateTransformations       A list of weighted biome transformations to apply to mutated biomes.
 * @param riverTransformations        A list of weighted biome transformations to apply to rivers.
 * @param shoreTransformations        A list of weighted biome transformations to apply to shores.
 * @param preHillsEdgeTransformations A list of conditional transformations to apply to the edges of hills.
 * @param postShoreTransformations    PostShoreEdgeTransformations is a list of conditional transformations to apply to the edges
 *                                    of shores.
 * @param climateTransformations      A list of weighted temperature transformations to apply to the biome's climate.
 */
public record BiomeOverworldGenRulesData(List<BiomeWeightedData> hillsTransformations, List<BiomeWeightedData> mutateTransformations,
                                         List<BiomeWeightedData> riverTransformations, List<BiomeWeightedData> shoreTransformations,
                                         List<BiomeConditionalTransformationData> preHillsEdgeTransformations,
                                         List<BiomeConditionalTransformationData> postShoreTransformations,
                                         List<BiomeWeightedTemperatureData> climateTransformations) {

}
