package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to make a specific 'boss event' occur in the world. It includes features such
 * as showing a boss bar to the player and turning the sky dark.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class BossEventPacket implements BedrockPacket {
    /**
     * BossEntityUniqueID is the unique ID of the boss entity that the boss event sent involves. By
     * default, the health percentage and title of the boss bar depend on the health and name tag of
     * this entity. If BossEntityUniqueID is the same as the client's entity unique ID, its
     * HealthPercentage and BossBarTitle can be freely altered.
     */
    private long bossUniqueEntityId;
    /**
     * The type of boss event to apply. The remaining fields that are encoded depend on this action.
     */
    private Action action;
    /**
     * The unique ID of the player to register to or unregister from the boss fight. This field is
     * only relevant for {@link Action#REGISTER_PLAYER}, {@link Action#UNREGISTER_PLAYER}, and
     * {@link Action#QUERY}.
     */
    private long playerUniqueEntityId;
    /**
     * The title shown above the boss bar. If {@link #bossUniqueEntityId} is the client's own entity
     * unique ID, this title can be set independently from the boss entity's name tag.
     */
    private String title;
    /**
     * The percentage of health that is shown in the boss bar (0.0-1.0). The HealthPercentage may be
     * set to a specific value if the BossEntityUniqueID matches the client's entity unique ID.
     */
    private float healthPercentage;
    /**
     * Controls how much the screen should darken while the boss bar is visible. This field
     * currently appears to have little or no visible effect.
     */
    private int darkenSky;
    /**
     * The colour of the boss bar that is shown when a player is subscribed. It is only set if the
     * EventType is BossEventShow, BossEventAppearanceProperties or BossEventTexture. This is
     * functional as of 1.18 and can be any of the BossEventColour constants listed above.
     */
    private int color;
    /**
     * The overlay of the boss bar that is shown on top of the boss bar when a player is subscribed.
     * It currently does not function. It is only set if the EventType is BossEventShow,
     * BossEventAppearanceProperties or BossEventTexture.
     */
    private int overlay;
    /**
     * A profanity-filtered version of {@link #title}. Clients with profanity filtering enabled use
     * this value when it is non-empty.
     *
     * @since v776
     */
    private String filteredTitle = "";

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BOSS_EVENT;
    }

    public enum Action {
        /**
         * Shows a boss bar for the player.
         */
        CREATE,
        /**
         * Registers a player as subscribed to the boss fight.
         */
        REGISTER_PLAYER,
        /**
         * Removes the boss bar from the client.
         */
        REMOVE,
        /**
         * Unregisters a player from the boss fight.
         */
        UNREGISTER_PLAYER,
        /**
         * Updates the boss bar percentage.
         */
        UPDATE_PERCENTAGE,
        /**
         * Updates the boss bar title.
         */
        UPDATE_NAME,
        /**
         * Updates properties such as screen darkening.
         */
        UPDATE_PROPERTIES,
        /**
         * Updates the boss bar colour and overlay. The overlay currently appears to have no visible
         * effect on the client.
         */
        UPDATE_STYLE,
        /**
         * Queries whether the player is currently subscribed to the boss event.
         */
        QUERY
    }

    @Override
    public BossEventPacket clone() {
        try {
            return (BossEventPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
