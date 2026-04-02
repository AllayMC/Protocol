package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent when a player dies.
 *
 * @param attackerEntityId  The attacker entity ID.
 * @param entityDamageCause The entity damage cause.
 * @param attackerVariant   The attacker variant.
 * @param inRaid            Whether in raid.
 */
public record PlayerDiedEventData(int attackerEntityId, int entityDamageCause, int attackerVariant, boolean inRaid) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.PLAYER_DIED;
    }
}
