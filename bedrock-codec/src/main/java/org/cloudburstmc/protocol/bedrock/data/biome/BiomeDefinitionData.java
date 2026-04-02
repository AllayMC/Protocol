package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.common.util.index.Indexable;
import org.cloudburstmc.protocol.common.util.index.Unindexed;

import java.awt.*;
import java.util.List;

/**
 * Represents a biome definition in the game. This can be a vanilla biome or a completely custom
 * biome.
 *
 * @param id               The numeric id of this biome. This field is always {@code null} for
 *                         vanilla biome, and is only set for custom biome (start at 30000).
 * @param temperature      The temperature of the biome, used for weather, biome behaviours and sky
 *                         colour.
 * @param downfall         The amount that precipitation affects colours and block changes.
 * @param redSporeDensity  The red spore density.
 * @param blueSporeDensity The blue spore density.
 * @param ashDensity       The ash density.
 * @param whiteAshDensity  The white ash density.
 * @param foliageSnow      The progression factor for foliage turning white due to snow.
 * @param depth            The depth of the biome.
 * @param scale            The scale of the biome.
 * @param mapWaterColor    An ARGB value for the water colour on maps in the biome.
 * @param rain             True if the biome has rain, false if it is a dry biome.
 * @param tags             Optional indexed or unindexed biome tags associated with this biome.
 * @param chunkGenData     The chunk gen data.
 */
public record BiomeDefinitionData(@Nullable Integer id, float temperature, float downfall,
                                  float redSporeDensity, float blueSporeDensity, float ashDensity,
                                  float whiteAshDensity, float foliageSnow, float depth, float scale,
                                  Color mapWaterColor, boolean rain, @Nullable Indexable<List<String>> tags,
                                  @Nullable BiomeDefinitionChunkGenData chunkGenData) {

    @JsonCreator
    public BiomeDefinitionData(@Nullable Integer id, float temperature, float downfall, float redSporeDensity,
                               float blueSporeDensity, float ashDensity, float whiteAshDensity, float foliageSnow,
                               float depth, float scale, Color mapWaterColor, boolean rain,
                               @Nullable List<String> tags, @Nullable BiomeDefinitionChunkGenData chunkGenData) {
        this(id, temperature, downfall, redSporeDensity, blueSporeDensity, ashDensity, whiteAshDensity,
                foliageSnow, depth, scale, mapWaterColor, rain, tags == null ? null : new Unindexed<>(tags),
                chunkGenData);
    }
}
