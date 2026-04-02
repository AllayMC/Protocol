package org.cloudburstmc.protocol.bedrock.data.command;

/**
 * Represents a message sent by a command that holds the output of one of the commands executed.
 *
 * @param success    Indicates if the output message was one of a successful command execution. If set to true, the
 *                   output message is by default colored white, whereas if set to false, the message is by default
 *                   colored red.
 * @param message    The raw message string or built-in translation key to display.
 * @param parameters A list of parameters that serve to supply the message sent with additional information, such
 *                   as the position that a player was teleported to or the effect that was applied to an entity.
 *                   These parameters only apply for the Minecraft built-in command output.
 */
public record CommandOutputMessage(boolean success, String message, String[] parameters) {
}
