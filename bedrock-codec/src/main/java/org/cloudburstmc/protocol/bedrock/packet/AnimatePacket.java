package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;

/**
 * Sent by the server to send a player animation from one player to all viewers of that player. It
 * is used for a couple of actions, such as arm swimming and critical hits.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AnimatePacket implements BedrockPacket {
    /**
     * The rowing time.
     *
     * @deprecated since v897
     */
    @Deprecated
    private float rowingTime;

    /**
     * The ID of the animation action to execute. It is one of the action type constants that may be
     * found above.
     */
    private Action action;
    /**
     * The runtime ID of the player that the animation should be played upon. The runtime ID is
     * unique for each world session, and entities are generally identified in packets using this
     * runtime ID.
     */
    private long runtimeEntityId;
    /**
     * Extra animation-specific data. For swing actions this usually carries the swing duration or
     * strength value introduced by newer protocol versions.
     *
     * @since v859
     */
    private float data;
    /**
     * The source category for swing actions.
     *
     * @since v898
     */
    private SwingSource swingSource = SwingSource.NONE;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ANIMATE;
    }

    public enum Action {
        NO_ACTION,
        SWING_ARM,
        WAKE_UP,
        CRITICAL_HIT,
        MAGIC_CRITICAL_HIT,
        /**
         * @deprecated v800 (1.21.80)
         */
        @Deprecated
        ROW_RIGHT,
        /**
         * @deprecated v800 (1.21.80)
         */
        @Deprecated
        ROW_LEFT,
    }

    public enum SwingSource {
        NONE("none"),
        BUILD("build"),
        MINE("mine"),
        INTERACT("interact"),
        ATTACK("attack"),
        USE_ITEM("useitem"),
        THROW_ITEM("throwitem"),
        DROP_ITEM("dropitem"),
        EVENT("event");
        private static final HashMap<String, SwingSource> BY_NAME = new HashMap<>();

        static {
            for (SwingSource value : values()) {
                BY_NAME.put(value.name, value);
            }
        }

        /**
         * The protocol string name written for this swing source.
         *
         * @since v898
         */
        @Getter
        private final String name;

        SwingSource(String name) {
            this.name = name;
        }

        public static SwingSource from(String name) {
            return BY_NAME.get(name);
        }
    }

    @Override
    public AnimatePacket clone() {
        try {
            return (AnimatePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
