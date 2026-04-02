package org.cloudburstmc.protocol.bedrock.data.definitions;

import lombok.experimental.NonFinal;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemVersion;

/**
 * Represents simple item definition used in the Bedrock protocol.
 *
 * @param identifier     The identifier.
 * @param runtimeId      The runtime ID.
 * @param version        The version.
 * @param componentBased Whether component based.
 * @param componentData  The component data.
 */
@NonFinal
public record SimpleItemDefinition(String identifier, int runtimeId, ItemVersion version, boolean componentBased,
                                   NbtMap componentData) implements ItemDefinition {
    // Backwards compatibility constructor
    public SimpleItemDefinition(String identifier, int runtimeId, boolean componentBased) {
        this(identifier, runtimeId, ItemVersion.LEGACY, componentBased, null);
    }
}
