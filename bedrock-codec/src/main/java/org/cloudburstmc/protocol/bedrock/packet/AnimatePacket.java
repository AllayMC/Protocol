package org.cloudburstmc.protocol.bedrock.packet;

import java.util.HashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to send a player animation from one player to all viewers of that player. It
 * is used for a couple of actions, such as arm swimming and critical hits.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AnimatePacket implements BedrockPacket {
    /**
     * @deprecated since v897
     */
    private float rowingTime;

    private Action action;
    private long runtimeEntityId;

    /**
     * @since v859
     */
    private float data;

    /**
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
        ROW_RIGHT,
        /**
         * @deprecated v800 (1.21.80)
         */
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
