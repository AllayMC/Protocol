package org.cloudburstmc.protocol.bedrock.data.clock;

import java.util.List;

/**
 * TimeMarkerData represents a time marker within a world clock.
 *
 * @param clockId     The clock ID.
 * @param timeMarkers The time markers.
 */
public record AddTimeMarkerData(long clockId, List<TimeMarkerData> timeMarkers) implements SyncWorldClocksPayload {

}
