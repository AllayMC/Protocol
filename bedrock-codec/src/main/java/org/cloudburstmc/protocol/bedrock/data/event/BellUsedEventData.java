package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent when a bell is used.
 *
 * @param itemId The item ID.
 */
public record BellUsedEventData(int itemId) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.BELL_USED;
    }
}
