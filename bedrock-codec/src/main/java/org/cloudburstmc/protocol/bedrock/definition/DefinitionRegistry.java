package org.cloudburstmc.protocol.bedrock.definition;

/**
 * A basic registry for protocol definitions that can be expanded upon.
 *
 * @param <D>
 */
public interface DefinitionRegistry<D extends Definition> {

    D getDefinition(int runtimeId);

    default D getDefinition(String identifier) {
        return null;
    }

    boolean isRegistered(D definition);
}
