package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.awt.*;

@Value
public class BiomeDefinitionData {
    @Nullable
    Integer id;
    float temperature;
    float downfall;
    float redSporeDensity;
    float blueSporeDensity;
    float ashDensity;
    float whiteAshDensity;
    float depth;
    float scale;
    Color mapWaterColor;
    boolean rain;
    int @Nullable [] tags;
    @Nullable
    BiomeDefinitionChunkGenData chunkGenData;
}
