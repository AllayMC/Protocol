package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent when a cauldron is used.
 *
 * @param potionId  The potion ID.
 * @param color     The color.
 * @param fillLevel The fill level.
 */
public record CauldronUsedEventData(int potionId, int color, int fillLevel) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.CAULDRON_USED;
    }
}
