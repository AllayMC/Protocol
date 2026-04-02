package org.cloudburstmc.protocol.bedrock.data.event;

import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;

/**
 * Represents called when a player drops gold ingots to a piglin to initiate a trade for an item.
 *
 * @param definition      The definition.
 * @param targetingPlayer Whether targeting player.
 */
public record PiglinBarterEventData(ItemDefinition definition, boolean targetingPlayer) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.PIGLIN_BARTER;
    }
}
