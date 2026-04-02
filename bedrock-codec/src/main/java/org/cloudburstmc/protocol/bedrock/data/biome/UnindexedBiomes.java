package org.cloudburstmc.protocol.bedrock.data.biome;

import org.cloudburstmc.protocol.common.util.index.Indexable;

import java.util.Map;

/**
 * Represents unindexed biomes used in the Bedrock protocol.
 */
final class UnindexedBiomes implements Indexable<Map<String, BiomeDefinitionData>> {
    /**
     * The biomes.
     */
    private final Map<String, BiomeDefinitionData> biomes;

    UnindexedBiomes(Map<String, BiomeDefinitionData> biomes) {
        this.biomes = biomes;
    }

    @Override
    public Map<String, BiomeDefinitionData> get() {
        return this.biomes;
    }
}
