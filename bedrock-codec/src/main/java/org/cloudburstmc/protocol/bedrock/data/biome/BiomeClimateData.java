package org.cloudburstmc.protocol.bedrock.data.biome;

/**
 * Represents the climate of a biome, mainly for ambience but also defines certain behaviours.
 *
 * @param temperature         The temperature of the biome, used for weather, biome behaviours and sky colour.
 * @param downfall            The amount that precipitation affects colours and block changes.
 * @param redSporeDensity     The red spore density.
 * @param blueSporeDensity    The blue spore density.
 * @param ashDensity          The ash density.
 * @param whiteAshDensity     The white ash density.
 * @param snowAccumulationMin The minimum amount of snow that can accumulate in the biome, every 0.125 is another layer of
 *                            snow.
 * @param snowAccumulationMax The maximum amount of snow that can accumulate in the biome, every 0.125 is another layer of
 *                            snow.
 */
public record BiomeClimateData(float temperature, float downfall, float redSporeDensity, float blueSporeDensity, float ashDensity,
                               float whiteAshDensity, float snowAccumulationMin, float snowAccumulationMax) {
}
