package org.cloudburstmc.protocol.bedrock.data;

/**
 * Client play modes reported in player input packets.
 */
public enum ClientPlayMode {
    /**
     * Standard in-world gameplay.
     */
    NORMAL,
    /**
     * Teaser/demo play mode.
     */
    TEASER,
    /**
     * Play mode used while the game is rendered on a separate screen.
     */
    SCREEN,
    /**
     * @deprecated since v859
     */
    @Deprecated
    VIEWER,
    /**
     * @deprecated since v859
     */
    @Deprecated
    REALITY,
    /**
     * @deprecated since v859
     */
    @Deprecated
    PLACEMENT,
    /**
     * @deprecated since v859
     */
    @Deprecated
    LIVING_ROOM,
    /**
     * The client is exiting the level.
     */
    EXIT_LEVEL,
    /**
     * @deprecated since v859
     */
    @Deprecated
    EXIT_LEVEL_LIVING_ROOM
}
