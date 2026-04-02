package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents an event sent by the server when a code builder scoreboard is updated.
 *
 * @param objectiveName The objective name.
 * @param score         The score.
 */
public record CodeBuilderScoreboardEventData(String objectiveName, int score) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.CODE_BUILDER_SCOREBOARD;
    }
}
