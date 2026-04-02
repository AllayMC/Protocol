package org.cloudburstmc.protocol.bedrock.data.event;

import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

/**
 * WaxedOrUnwaxedCopperEvent is an event sent by the server when a copper block is waxed or unwaxed.
 *
 * @param definition The definition.
 */
public record CopperWaxedOrUnwaxedEventData(BlockDefinition definition) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.COPPER_WAXED_OR_UNWAXED;
    }
}
