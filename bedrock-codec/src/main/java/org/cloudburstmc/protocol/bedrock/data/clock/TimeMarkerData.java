package org.cloudburstmc.protocol.bedrock.data.clock;

/**
 * Represents a time marker within a world clock.
 *
 * @param id     The unique identifier for the time marker.
 * @param name   The name of the time marker.
 * @param time   The time at which the marker is set.
 * @param period changelog says required but whatever
 */
public record TimeMarkerData(long id, String name, int time, Integer period) {

}
