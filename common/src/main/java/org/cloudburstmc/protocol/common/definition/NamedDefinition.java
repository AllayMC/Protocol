package org.cloudburstmc.protocol.common.definition;

public interface NamedDefinition extends Definition {
    /**
     * The identifier of this definition.
     *
     * @return identifier
     */
    String identifier();
}
