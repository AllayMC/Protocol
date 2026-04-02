package org.cloudburstmc.protocol.bedrock.data.clock;


import java.util.List;

/**
 * Represents a complete world clock with its time markers.
 *
 * @param id          The unique identifier for the clock.
 * @param name        The name of the clock.
 * @param time        The current time of the clock.
 * @param paused      Paused indicates if the clock is paused.
 * @param timeMarkers A list of time markers for this clock.
 */
public record WorldClockData(long id, String name, int time, boolean paused, List<TimeMarkerData> timeMarkers) {
}
