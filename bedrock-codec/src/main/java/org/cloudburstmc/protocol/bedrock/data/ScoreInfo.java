package org.cloudburstmc.protocol.bedrock.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static org.cloudburstmc.protocol.bedrock.util.Preconditions.checkArgument;

/**
 * Represents one scoreboard entry transmitted in {@code SetScorePacket}.
 */
@Getter
@EqualsAndHashCode
@ToString
public class ScoreInfo {
    /**
     * The unique scoreboard entry ID.
     */
    private final long scoreboardId;
    /**
     * The objective identifier that owns this score.
     */
    private final String objectiveId;
    /**
     * The numeric score value.
     */
    private final int score;
    /**
     * The scorer type represented by this entry.
     */
    private final ScorerType type;
    /**
     * The fake-player name, used when {@link #type} is {@link ScorerType#FAKE}.
     */
    private final String name;
    /**
     * The unique entity ID, used when {@link #type} is {@link ScorerType#PLAYER} or
     * {@link ScorerType#ENTITY}.
     */
    private final long entityId;

    public ScoreInfo(long scoreboardId, String objectiveId, int score) {
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = ScorerType.INVALID;
        this.name = null;
        this.entityId = -1;
    }

    public ScoreInfo(long scoreboardId, String objectiveId, int score, String name) {
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = ScorerType.FAKE;
        this.name = name;
        this.entityId = -1;
    }

    public ScoreInfo(long scoreboardId, String objectiveId, int score, ScorerType type, long entityId) {
        checkArgument(type == ScorerType.ENTITY || type == ScorerType.PLAYER, "Must be player or entity");
        this.scoreboardId = scoreboardId;
        this.objectiveId = objectiveId;
        this.score = score;
        this.type = type;
        this.entityId = entityId;
        this.name = null;
    }

    public enum ScorerType {
        /**
         * No scorer information is present.
         */
        INVALID,
        /**
         * The score belongs to a player entity.
         */
        PLAYER,
        /**
         * The score belongs to a non-player entity.
         */
        ENTITY,
        /**
         * The score belongs to a fake player entry identified only by name.
         */
        FAKE
    }
}
