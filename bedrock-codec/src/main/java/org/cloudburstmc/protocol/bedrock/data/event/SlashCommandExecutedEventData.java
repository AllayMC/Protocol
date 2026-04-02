package org.cloudburstmc.protocol.bedrock.data.event;

import java.util.List;

/**
 * Represents slash command executed event data used in the Bedrock protocol.
 *
 * @param commandName    The command name.
 * @param successCount   The success count.
 * @param outputMessages The output messages.
 */
public record SlashCommandExecutedEventData(String commandName, int successCount, List<String> outputMessages) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.SLASH_COMMAND_EXECUTED;
    }
}
