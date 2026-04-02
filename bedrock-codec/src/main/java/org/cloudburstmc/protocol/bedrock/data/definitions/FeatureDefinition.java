package org.cloudburstmc.protocol.bedrock.data.definitions;

/**
 * Describes a world generation feature entry sent in the feature registry for client-side terrain
 * generation.
 *
 * @param name the feature identifier
 * @param json the serialized feature definition payload
 */
public record FeatureDefinition(String name, String json) {
}
