package org.cloudburstmc.protocol.bedrock.data.biome;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

import java.util.List;

/**
 * BiomeCappedSurface specifies the materials to use for the capped surface of a biome, such as in
 * the Nether.
 *
 * @param floorBlocks     A list of block definitions to use for the floor blocks.
 * @param ceilingBlocks   A list of block definitions to use for the ceiling blocks.
 * @param seaBlock        An optional block definition to use for the sea block.
 * @param foundationBlock An optional block definition to use for the foundation block.
 * @param beachBlock      An optional block definition to use for the beach block.
 */
public record BiomeCappedSurfaceData(List<BlockDefinition> floorBlocks, List<BlockDefinition> ceilingBlocks, @Nullable BlockDefinition seaBlock,
                                     @Nullable BlockDefinition foundationBlock, @Nullable BlockDefinition beachBlock) {
}
