package org.cloudburstmc.protocol.bedrock.data.clock;


/**
 * Represents the state data for synchronising a world clock.
 *
 * @param clockId The unique identifier for the clock.
 * @param time    The current time of the clock.
 * @param paused  Paused indicates if the clock is paused.
 */
public record SyncWorldClockStateData(long clockId, int time, boolean paused) {

}
