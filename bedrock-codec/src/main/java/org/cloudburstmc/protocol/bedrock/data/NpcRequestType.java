package org.cloudburstmc.protocol.bedrock.data;

/**
 * NPCRequest is sent by the client when it interacts with an NPC. The packet is specifically made
 * for Education Edition, where NPCs are available to use.
 */
public enum NpcRequestType {
    SET_ACTION,
    EXECUTE_COMMAND_ACTION,
    EXECUTE_CLOSING_COMMANDS,
    SET_NAME,
    SET_SKIN,
    SET_INTERACTION_TEXT,
    EXECUTE_OPENING_COMMANDS
}
