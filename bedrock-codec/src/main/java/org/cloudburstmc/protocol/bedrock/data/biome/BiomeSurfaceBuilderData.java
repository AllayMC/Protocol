package org.cloudburstmc.protocol.bedrock.data.biome;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Surface builder parameters used by the v974 biome-definition codec.
 */
public record BiomeSurfaceBuilderData(@Nullable BiomeSurfaceMaterialData surfaceMaterial,
                                      boolean hasDefaultOverworldSurface,
                                      boolean hasSwampSurface,
                                      boolean hasFrozenOceanSurface,
                                      boolean hasTheEndSurface,
                                      @Nullable BiomeMesaSurfaceData mesaSurface,
                                      @Nullable BiomeCappedSurfaceData cappedSurface,
                                      @Nullable BiomeNoiseGradientSurfaceData noiseGradientSurface) {
}
