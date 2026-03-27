package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make a specific 'boss event' occur in the world. It includes features such
 * as showing a boss bar to the player and turning the sky dark.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class BossEventPacket implements BedrockPacket {
    private long bossUniqueEntityId;
    private Action action;
    private long playerUniqueEntityId;
    private CharSequence title;
    /**
     * @since v776
     */
    private CharSequence filteredTitle = "";

    private float healthPercentage;
    private int darkenSky;
    private int color;
    private int overlay;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BOSS_EVENT;
    }

    public enum Action {
        /**
         * Creates the bossbar to the player.
         */
        CREATE,
        /**
         * Registers a player to a boss fight.
         */
        REGISTER_PLAYER,
        /**
         * Removes the bossbar from the client.
         */
        REMOVE,
        /**
         * Unregisters a player from a boss fight.
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
         * Darken the sky when the boss bar is shown.
         */
        UPDATE_PROPERTIES,
        /**
         * Intended to alter the boss bar appearance, but these currently produce no visible effect on
         * the client.
         */
        UPDATE_STYLE,
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

    public String getTitle() {
        return getTitle(String.class);
    }

    public <T extends CharSequence> T getTitle(Class<T> type) {
        return type.cast(title);
    }

    public String getFilteredTitle() {
        return getFilteredTitle(String.class);
    }

    public <T extends CharSequence> T getFilteredTitle(Class<T> type) {
        return type.cast(filteredTitle);
    }
}
