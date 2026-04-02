package org.cloudburstmc.protocol.bedrock.data;

/**
 * Represents an in-progress packet. We currently do not know the use case.
 */
public enum SimulationType {
    GAME,
    EDITOR,
    TEST
}
