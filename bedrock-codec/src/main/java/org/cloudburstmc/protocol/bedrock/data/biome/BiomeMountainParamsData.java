package org.cloudburstmc.protocol.bedrock.data.biome;

import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

/**
 * Represents biome mountain params data used in the Bedrock protocol.
 *
 * @param steepBlock      The steep block.
 * @param northSlopes     Whether north slopes.
 * @param southSlopes     Whether south slopes.
 * @param westSlopes      Whether west slopes.
 * @param eastSlopes      Whether east slopes.
 * @param topSlideEnabled Whether top slide enabled.
 */
public record BiomeMountainParamsData(BlockDefinition steepBlock, boolean northSlopes, boolean southSlopes, boolean westSlopes, boolean eastSlopes,
                                      boolean topSlideEnabled) {

}
