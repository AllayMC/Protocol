package org.cloudburstmc.protocol.bedrock.data.event;

/**
 * Represents the event data sent for achievements.
 *
 * @param achievementId The ID for the achievement.
 */
public record AchievementAwardedEventData(int achievementId) implements EventData {
    @Override
    public EventDataType getType() {
        return EventDataType.ACHIEVEMENT_AWARDED;
    }
}
