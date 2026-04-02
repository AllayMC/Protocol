package org.cloudburstmc.protocol.bedrock.data.clock;

import java.util.List;

/**
 * SyncWorldClockStateData represents the state data for synchronising a world clock.
 *
 * @param clockData The clock data.
 */
public record SyncStateData(List<SyncWorldClockStateData> clockData) implements SyncWorldClocksPayload {

}
