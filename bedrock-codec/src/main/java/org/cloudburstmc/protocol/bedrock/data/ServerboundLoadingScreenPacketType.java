package org.cloudburstmc.protocol.bedrock.data;

/**
 * Enumerates loading screen lifecycle events reported by the client.
 */
public enum ServerboundLoadingScreenPacketType {
    /**
     * An unknown or unspecified loading screen event.
     */
    UNKNOWN,
    /**
     * A loading screen has started.
     */
    START_LOADING_SCREEN,
    /**
     * A loading screen has finished.
     */
    END_LOADING_SCREEN
}
