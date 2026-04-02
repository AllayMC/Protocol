package org.cloudburstmc.protocol.bedrock.data.biome;

import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

/**
 * BiomeSurfaceMaterial specifies the materials to use for the surface layers of the biome.
 *
 * @param topBlock        The block definition to use for the top layer.
 * @param midBlock        The block definition to use for the middle layers.
 * @param seaFloorBlock   The block definition to use for the sea floor.
 * @param foundationBlock The block definition to use for the foundation layers.
 * @param seaBlock        The block definition to use for the sea layers.
 * @param seaFloorDepth   The depth of the sea floor, in blocks.
 */
public record BiomeSurfaceMaterialData(BlockDefinition topBlock, BlockDefinition midBlock, BlockDefinition seaFloorBlock,
                                       BlockDefinition foundationBlock, BlockDefinition seaBlock, int seaFloorDepth) {

}
