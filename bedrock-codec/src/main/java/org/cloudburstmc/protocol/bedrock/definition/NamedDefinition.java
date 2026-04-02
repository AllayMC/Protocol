package org.cloudburstmc.protocol.bedrock.definition;

public interface NamedDefinition extends Definition {
    /**
     * The identifier of this definition.
     *
     * @return identifier
     */
    String identifier();
}
