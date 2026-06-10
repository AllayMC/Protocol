package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Represents the event data sent when an agent is created.
 *
 * @deprecated since v1001
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Deprecated
public class AgentCreatedEventData implements EventData {
    public static final AgentCreatedEventData INSTANCE = new AgentCreatedEventData();

    @Override
    public EventDataType getType() {
        return EventDataType.AGENT_CREATED;
    }
}
