package org.cloudburstmc.protocol.bedrock.data.biome;

import java.util.List;

/**
 * Represents biome legacy world gen rules data used in the Bedrock protocol.
 *
 * @param legacyPreHills The legacy pre hills.
 */
public record BiomeLegacyWorldGenRulesData(List<BiomeConditionalTransformationData> legacyPreHills) {
}
