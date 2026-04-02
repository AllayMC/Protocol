package org.cloudburstmc.protocol.bedrock.data;

/**
 * Interaction models reported by the client for player input handling.
 */
public enum InputInteractionModel {
    /**
     * Touch-first interactions.
     */
    TOUCH,
    /**
     * Crosshair-based interactions.
     */
    CROSSHAIR,
    /**
     * Classic interactions.
     */
    CLASSIC
}
