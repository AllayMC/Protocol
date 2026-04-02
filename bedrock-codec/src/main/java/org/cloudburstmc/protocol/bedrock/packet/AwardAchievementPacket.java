package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to award an achievement to a player.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AwardAchievementPacket implements BedrockPacket {
    /**
     * The numeric ID of the achievement that should be awarded to the player. The values for these
     * IDs are currently unknown.
     */
    private int achievementId;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.AWARD_ACHIEVEMENT;
    }

    @Override
    public AwardAchievementPacket clone() {
        try {
            return (AwardAchievementPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
