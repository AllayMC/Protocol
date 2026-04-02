package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents pattern removed event data used in the Bedrock protocol.
 *
 * @param itemId       The item ID.
 * @param auxValue     The aux value.
 * @param patternsSize The patterns size.
 * @param patternIndex The pattern index.
 * @param patternColor The pattern color.
 */
public record PatternRemovedEventData(int itemId, int auxValue, int patternsSize, int patternIndex, int patternColor) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.PATTERN_REMOVED;
    }
}
