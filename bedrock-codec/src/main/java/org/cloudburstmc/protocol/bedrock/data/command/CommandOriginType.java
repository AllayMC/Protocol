package org.cloudburstmc.protocol.bedrock.data.command;

/**
 * CommandOrigin holds data that identifies the origin of the requesting of a command. It holds
 * several fields that may be used to get specific information. When sent in a CommandRequest
 * packet, the same CommandOrigin should be sent in a CommandOutput packet.
 */
public enum CommandOriginType {
    PLAYER,
    BLOCK,
    MINECART_BLOCK,
    DEV_CONSOLE,
    TEST,
    AUTOMATION_PLAYER,
    CLIENT_AUTOMATION,
    DEDICATED_SERVER,
    ENTITY,
    VIRTUAL,
    GAME_ARGUMENT,
    ENTITY_SERVER,
    PRECOMPILED,
    GAME_DIRECTOR_ENTITY_SERVER,
    SCRIPT,
    EXECUTE_CONTEXT
}
