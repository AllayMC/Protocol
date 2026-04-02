package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents mob born event data used in the Bedrock protocol.
 *
 * @param entityType The entity type.
 * @param variant    The variant.
 * @param color      The color.
 */
public record MobBornEventData(int entityType, int variant, int color) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.MOB_BORN;
    }
}
