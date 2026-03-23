package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PlayerDiedEventData implements EventData {
    private final int attackerEntityId;

    /**
     * @since v389
     */
    private final int attackerVariant;
    private final int entityDamageCause;

    /**
     * @since v389
     */
    private final boolean inRaid;

    @Override
    public EventDataType getType() {
        return EventDataType.PLAYER_DIED;
    }
}
