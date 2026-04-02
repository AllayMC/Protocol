package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * CodeBuilderRuntimeActionEvent is an event sent by the server when a code builder runtime action
 * is performed.
 *
 * @param action The action to perform.
 */
public record CodeBuilderActionEventData(String action) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.CODE_BUILDER_ACTION;
    }
}
