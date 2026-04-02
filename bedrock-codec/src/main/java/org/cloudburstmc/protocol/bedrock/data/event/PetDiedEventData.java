package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent when a pet dies.
 *
 * @param ownerKilled          Whether owner killed.
 * @param killerUniqueEntityId The killer unique entity ID.
 * @param petUniqueEntityId    The pet unique entity ID.
 * @param entityDamageCause    The entity damage cause.
 * @param petEntityType        The pet entity type.
 */
public record PetDiedEventData(boolean ownerKilled, long killerUniqueEntityId, long petUniqueEntityId, int entityDamageCause,
                               int petEntityType) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.PET_DIED;
    }
}
