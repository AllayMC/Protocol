package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents an event used for an unknown purpose.
 *
 * @param eventName The event name.
 */
public record EntityDefinitionTriggerEventData(String eventName) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.ENTITY_DEFINITION_TRIGGER;
    }
}
