package org.cloudburstmc.protocol.bedrock.data.biome;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * Represents biome definition chunk gen data used in the Bedrock protocol.
 *
 * @param climate                    The climate.
 * @param consolidatedFeatures       The consolidated features.
 * @param mountainParams             The mountain params.
 * @param surfaceMaterialAdjustment  The surface material adjustment.
 * @param surfaceMaterial            The surface material.
 * @param hasDefaultOverworldSurface Whether has default overworld surface.
 * @param hasSwampSurface            Whether has swamp surface.
 * @param hasFrozenOceanSurface      Whether has frozen ocean surface.
 * @param hasTheEndSurface           Whether has the end surface.
 * @param mesaSurface                The mesa surface.
 * @param cappedSurface              The capped surface.
 * @param overworldGenRules          The overworld gen rules.
 * @param multinoiseGenRules         The multinoise gen rules.
 * @param legacyWorldGenRules        The legacy world gen rules.
 * @param biomeReplacementData       The biome replacement data.
 * @param villageType                The village type.
 */
public record BiomeDefinitionChunkGenData(@Nullable BiomeClimateData climate, @Nullable List<BiomeConsolidatedFeatureData> consolidatedFeatures,
                                          @Nullable BiomeMountainParamsData mountainParams,
                                          @Nullable BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment,
                                          @Nullable BiomeSurfaceMaterialData surfaceMaterial, boolean hasDefaultOverworldSurface,
                                          boolean hasSwampSurface, boolean hasFrozenOceanSurface, boolean hasTheEndSurface,
                                          @Nullable BiomeMesaSurfaceData mesaSurface, @Nullable BiomeCappedSurfaceData cappedSurface,
                                          @Nullable BiomeOverworldGenRulesData overworldGenRules,
                                          @Nullable BiomeMultinoiseGenRulesData multinoiseGenRules,
                                          @Nullable BiomeLegacyWorldGenRulesData legacyWorldGenRules,
                                          @Nullable BiomeReplacementData biomeReplacementData, @Nullable Number villageType) {
}
