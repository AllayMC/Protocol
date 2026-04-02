package org.cloudburstmc.protocol.bedrock.data.biome;

import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

/**
 * BiomeMesaSurface specifies the materials to use for the mesa biome.
 *
 * @param clayMaterial     The block definition to use for clay layers.
 * @param hardClayMaterial The block definition to use for hard clay layers.
 * @param brycePillars     True if the biome has bryce pillars, which are tall spire-like structures.
 * @param hasForest        True if the biome has a forest.
 */
public record BiomeMesaSurfaceData(BlockDefinition clayMaterial, BlockDefinition hardClayMaterial, boolean brycePillars, boolean hasForest) {

}
