package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents movement corrected event data used in the Bedrock protocol.
 *
 * @param positionDelta     The position delta.
 * @param cheatingScore     The cheating score.
 * @param scoreThreshold    The score threshold.
 * @param distanceThreshold The distance threshold.
 * @param durationThreshold The duration threshold.
 */
public record MovementCorrectedEventData(float positionDelta, float cheatingScore, float scoreThreshold, float distanceThreshold,
                                         int durationThreshold) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.MOVEMENT_CORRECTED;
    }
}
