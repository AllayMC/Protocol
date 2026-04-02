package org.cloudburstmc.protocol.bedrock.data.command;

import lombok.NonNull;

/**
 * Represents a message sent by a command that holds the output of one of the commands executed.
 *
 * @param internal   The protocol-level internal flag stored on this output entry. The exact client
 *                   meaning is not clearly documented.
 * @param messageId  The raw message string or built-in translation key to display.
 * @param parameters A list of parameters that serve to supply the message sent with additional information, such
 *                   as the position that a player was teleported to or the effect that was applied to an entity.
 *                   These parameters only apply for the Minecraft built-in command output.
 */
public record CommandOutputMessage(boolean internal, @NonNull String messageId, @NonNull String[] parameters) {
}
