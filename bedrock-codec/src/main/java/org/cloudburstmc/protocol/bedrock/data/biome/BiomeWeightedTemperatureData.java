package org.cloudburstmc.protocol.bedrock.data.biome;

/**
 * Represents biome weighted temperature data used in the Bedrock protocol.
 *
 * @param temperature The temperature.
 * @param weight      The weight.
 */
public record BiomeWeightedTemperatureData(BiomeTemperatureCategory temperature, long weight) {

}
