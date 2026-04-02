package org.cloudburstmc.protocol.bedrock.data.biome;

/**
 * Represents biome multinoise gen rules data used in the Bedrock protocol.
 *
 * @param temperature The temperature.
 * @param humidity    The humidity.
 * @param altitude    The altitude.
 * @param weirdness   The weirdness.
 * @param weight      The weight.
 */
public record BiomeMultinoiseGenRulesData(float temperature, float humidity, float altitude, float weirdness, float weight) {

}
