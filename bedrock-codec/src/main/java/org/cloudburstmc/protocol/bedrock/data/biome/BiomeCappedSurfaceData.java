package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

import java.util.List;

@Value
public class BiomeCappedSurfaceData {
    List<BlockDefinition> floorBlocks;
    List<BlockDefinition> ceilingBlocks;
    @Nullable
    BlockDefinition seaBlock;
    @Nullable
    BlockDefinition foundationBlock;
    @Nullable
    BlockDefinition beachBlock;
}
