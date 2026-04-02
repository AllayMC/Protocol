package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent when a portal is built.
 *
 * @param dimensionId The dimension ID.
 */
public record PortalBuiltEventData(int dimensionId) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.PORTAL_BUILT;
    }
}
