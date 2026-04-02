package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent when a fish is bucketed.
 *
 * @param pattern            The pattern.
 * @param preset             The preset.
 * @param bucketedEntityType The bucketed entity type.
 * @param releaseEvent       Whether release event.
 */
public record FishBucketedEventData(int pattern, int preset, int bucketedEntityType, boolean releaseEvent) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.FISH_BUCKETED;
    }
}
