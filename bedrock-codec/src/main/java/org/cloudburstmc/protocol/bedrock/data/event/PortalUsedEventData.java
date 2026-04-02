package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent when a portal is used.
 *
 * @param fromDimensionId The from dimension ID.
 * @param toDimensionId   The to dimension ID.
 */
public record PortalUsedEventData(int fromDimensionId, int toDimensionId) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.PORTAL_USED;
    }
}
