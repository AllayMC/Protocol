package org.cloudburstmc.protocol.bedrock.data;

/**
 * Ability identifiers used by the Bedrock protocol when synchronizing or requesting player
 * abilities.
 */
public enum Ability {
    BUILD,
    MINE,
    DOORS_AND_SWITCHES,
    OPEN_CONTAINERS,
    ATTACK_PLAYERS,
    ATTACK_MOBS,
    OPERATOR_COMMANDS,
    TELEPORT,
    INVULNERABLE,
    FLYING,
    MAY_FLY,
    INSTABUILD,
    LIGHTNING,
    FLY_SPEED,
    WALK_SPEED,
    MUTED,
    WORLD_BUILDER,
    NO_CLIP,
    /**
     * @since v575
     */
    PRIVILEGED_BUILDER,
    /**
     * @since v776
     */
    VERTICAL_FLY_SPEED;

    /**
     * Wire type used to serialize the value associated with an {@link Ability}.
     */
    public enum Type {
        NONE,
        BOOLEAN,
        FLOAT
    }
}
