package org.cloudburstmc.protocol.bedrock.data.clock;

import java.util.ArrayList;
import java.util.List;

/**
 * TimeMarkerData represents a time marker within a world clock.
 *
 * @param clockId       The clock ID.
 * @param timeMarkerIds The time marker ids.
 */
public record RemoveTimeMarkerData(long clockId, List<Long> timeMarkerIds) implements SyncWorldClocksPayload {

    public RemoveTimeMarkerData(long clockId) {
        this(clockId, new ArrayList<>());
    }
}
