package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents an event used in Education Edition.
 *
 * @param result    The result.
 * @param command   The command.
 * @param dataKey   The data key.
 * @param dataValue The data value.
 * @param output    The output.
 */
public record AgentCommandEventData(AgentResult result, String command, String dataKey, int dataValue, String output) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.AGENT_COMMAND;
    }
}
