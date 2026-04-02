package org.cloudburstmc.protocol.bedrock.data.definitions;

import org.cloudburstmc.protocol.bedrock.definition.NamedDefinition;

/**
 * Represents simple named definition used in the Bedrock protocol.
 *
 * @param identifier The identifier.
 * @param runtimeId  The runtime ID.
 */
public record SimpleNamedDefinition(String identifier, int runtimeId) implements NamedDefinition {
}
