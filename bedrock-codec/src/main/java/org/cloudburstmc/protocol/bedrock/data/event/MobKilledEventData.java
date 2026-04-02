package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent when a mob is killed.
 *
 * @param killerUniqueEntityId The killer unique entity ID.
 * @param victimUniqueEntityId The victim unique entity ID.
 * @param entityDamageCause    The entity damage cause.
 * @param villagerTradeTier    VillagerTradeTier -1 if not a trading actor.
 * @param villagerDisplayName  VillagerDisplayName Empty if not a trading actor.
 * @param killerEntityType     The killer entity type.
 */
public record MobKilledEventData(long killerUniqueEntityId, long victimUniqueEntityId, int entityDamageCause,
                                 int villagerTradeTier, String villagerDisplayName, int killerEntityType)
        implements EventData {

    public MobKilledEventData(long killerUniqueEntityId, long victimUniqueEntityId, int entityDamageCause,
                              int killerEntityType) {
        this(killerUniqueEntityId, victimUniqueEntityId, entityDamageCause, -1, "", killerEntityType);
    }

    @Override
    public EventDataType getType() {
        return EventDataType.MOB_KILLED;
    }
}
