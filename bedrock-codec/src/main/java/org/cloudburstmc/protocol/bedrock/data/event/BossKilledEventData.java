package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent when a boss dies.
 *
 * @param bossUniqueEntityId The boss unique entity ID.
 * @param playerPartySize    The player party size.
 * @param bossEntityType     The boss entity type.
 */
public record BossKilledEventData(long bossUniqueEntityId, int playerPartySize, int bossEntityType) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.BOSS_KILLED;
    }
}
