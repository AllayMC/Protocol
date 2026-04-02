package org.cloudburstmc.protocol.bedrock.data.event;

import org.cloudburstmc.protocol.bedrock.data.BlockInteractionType;

/**
 * Represents the event data sent when a composter is interacted with.
 *
 * @param blockInteractionType The block interaction type.
 * @param itemId               The item ID.
 */
public record ComposterInteractEventData(BlockInteractionType blockInteractionType, int itemId) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.COMPOSTER_INTERACT;
    }
}
