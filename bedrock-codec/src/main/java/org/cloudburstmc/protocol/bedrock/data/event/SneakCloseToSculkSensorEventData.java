package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Represents sneak close to sculk sensor event data used in the Bedrock protocol.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SneakCloseToSculkSensorEventData implements EventData {
    public static final SneakCloseToSculkSensorEventData INSTANCE = new SneakCloseToSculkSensorEventData();

    @Override
    public EventDataType getType() {
        return EventDataType.SNEAK_CLOSE_TO_SCULK_SENSOR;
    }
}
