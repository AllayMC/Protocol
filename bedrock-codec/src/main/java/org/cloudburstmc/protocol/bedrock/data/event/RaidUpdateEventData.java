package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents an event used to update a raids progress client side.
 *
 * @param currentWave The current wave.
 * @param totalWaves  The total waves.
 * @param winner      Whether winner.
 */
public record RaidUpdateEventData(int currentWave, int totalWaves, boolean winner) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.RAID_UPDATE;
    }
}
