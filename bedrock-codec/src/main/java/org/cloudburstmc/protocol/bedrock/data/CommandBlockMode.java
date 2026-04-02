package org.cloudburstmc.protocol.bedrock.data;

/**
 * CommandBlockUpdate is sent by the client to update a command block at a specific position. The
 * command block may be either a physical block or an entity.
 */
public enum CommandBlockMode {
    NORMAL,
    REPEATING,
    CHAIN
}
