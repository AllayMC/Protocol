package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents item used event data used in the Bedrock protocol.
 *
 * @param itemId    The item ID.
 * @param itemAux   The item aux.
 * @param useMethod The use method.
 * @param useCount  The use count.
 */
public record ItemUsedEventData(short itemId, int itemAux, int useMethod, int useCount) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.ITEM_USED_EVENT;
    }
}
