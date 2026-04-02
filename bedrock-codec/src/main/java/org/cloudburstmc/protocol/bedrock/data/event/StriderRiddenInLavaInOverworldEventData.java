package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Represents an event sent by the server when a strider is ridden in lava in the overworld.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StriderRiddenInLavaInOverworldEventData implements EventData {
    public static final StriderRiddenInLavaInOverworldEventData INSTANCE = new StriderRiddenInLavaInOverworldEventData();

    @Override
    public EventDataType getType() {
        return EventDataType.STRIDER_RIDDEN_IN_LAVA_IN_OVERWORLD;
    }
}
