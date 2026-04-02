package org.cloudburstmc.protocol.bedrock.data.event;

import org.cloudburstmc.protocol.bedrock.data.BlockInteractionType;

/**
 * Represents cauldron interact event data used in the Bedrock protocol.
 *
 * @param blockInteractionType The block interaction type.
 * @param itemId               The item ID.
 */
public record CauldronInteractEventData(BlockInteractionType blockInteractionType, int itemId) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.CAULDRON_INTERACT;
    }
}
