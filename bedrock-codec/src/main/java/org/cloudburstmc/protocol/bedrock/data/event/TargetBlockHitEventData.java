package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents target block hit event data used in the Bedrock protocol.
 *
 * @param redstoneLevel The redstone level.
 */
public record TargetBlockHitEventData(int redstoneLevel) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.TARGET_BLOCK_HIT;
    }
}
