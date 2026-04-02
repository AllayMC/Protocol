package org.cloudburstmc.protocol.bedrock.data;

/**
 * SimpleEvent is used for enabling or disabling commands and for unlocking world template settings
 * (both unlocking UI buttons on client and the actual setting on the server). This is fired from
 * the client to the server and a SetCommandsEnabled is sent back when enabling commands.
 */
public enum SimpleEventType {
    /**
     * No-op value used when no specific simple event is being reported.
     */
    NONE,
    /**
     * Requests that commands be enabled for the current world.
     */
    ENABLE_COMMANDS,
    /**
     * Requests that commands be disabled for the current world.
     */
    DISABLE_COMMANDS,
    /**
     * Unlocks world-template settings in the client UI and on the server.
     */
    UNLOCK_WORLD_TEMPLATE_SETTINGS
}
