package org.cloudburstmc.protocol.bedrock.data.definitions;

import lombok.Data;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.common.definition.NamedDefinition;

import java.util.TreeMap;

/**
 * Represents simple block definition used in the Bedrock protocol.
 */
@Data
public class SimpleBlockDefinition implements BlockDefinition, NamedDefinition {
    /**
     * The identifier.
     */
    private final String identifier;
    /**
     * The runtime ID.
     */
    private final int runtimeId;
    /**
     * The state.
     */
    private final NbtMap state;

    // Cache identifier as this implementation is immutable
    /**
     * The persistent identifier.
     */
    private transient String persistentIdentifier;

    @Override
    public String identifier() {
        return this.identifier;
    }

    @Override
    public int runtimeId() {
        return this.runtimeId;
    }

    public String getPersistentIdentifier() {
        if (this.persistentIdentifier == null) {
            // This is not most performant solution,
            // but will make sure the identifier is always equal for the block state
            StringBuilder builder = new StringBuilder(this.getIdentifier());
            if (!this.getState().isEmpty()) {
                TreeMap<String, String> properties = new TreeMap<>();
                NbtMap states = getState().getCompound("states");
                for (String stateName : states.keySet()) {
                    String value = states.get(stateName).toString();
                    properties.put(stateName, value);
                }
                properties.forEach((name, state) -> builder.append("|").append(name).append("=").append(state));
            }
            this.persistentIdentifier = builder.toString();
        }
        return this.persistentIdentifier;
    }
}
