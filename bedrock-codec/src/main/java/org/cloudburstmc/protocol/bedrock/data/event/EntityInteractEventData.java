package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent for entity interactions.
 *
 * @param interactedEntityID The interacted entity ID.
 * @param interactionType    The interaction type.
 * @param legacyEntityTypeId The legacy entity type ID.
 * @param variant            The variant.
 * @param paletteColor       The palette color.
 */
public record EntityInteractEventData(long interactedEntityID, int interactionType, int legacyEntityTypeId, int variant,
                                      int paletteColor) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.ENTITY_INTERACT;
    }
}
