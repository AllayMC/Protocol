package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents an event used to detect movement anomalies.
 *
 * @param eventType            The event type.
 * @param cheatingScore        The cheating score.
 * @param averagePositionDelta The average position delta.
 * @param totalPositionDelta   The total position delta.
 * @param minPositionDelta     The min position delta.
 * @param maxPositionDelta     The max position delta.
 */
public record MovementAnomalyEventData(int eventType, float cheatingScore, float averagePositionDelta, float totalPositionDelta,
                                       float minPositionDelta, float maxPositionDelta) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.MOVEMENT_ANOMALY;
    }
}
