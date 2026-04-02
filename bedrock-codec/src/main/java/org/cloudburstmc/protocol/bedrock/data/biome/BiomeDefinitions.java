package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

/**
 * Container for biome definitions used by the structured biome definition packets introduced in
 * newer Bedrock protocol versions.
 *
 * @param definitions Backing biome definitions indexed by identifier.
 */
public record BiomeDefinitions(Map<String, BiomeDefinitionData> definitions) {

    @JsonCreator
    public BiomeDefinitions {
    }
}
