package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.*;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to update the client-side player list in the in-game menu screen. It shows the
 * icon of each player if the correct XUID is written in the packet. Sending the PlayerList packet
 * is obligatory when sending an AddPlayer packet. The added player will not show up to a client if
 * it has not been added to the player list, because several properties of the player are obtained
 * from the player list, such as the skin.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerListPacket implements BedrockPacket {
    private final List<Entry> entries = new ObjectArrayList<>();
    private Action action;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_LIST;
    }

    public enum Action {
        ADD,
        REMOVE
    }

    @Data
    @ToString(doNotUseGetters = true)
    @EqualsAndHashCode(doNotUseGetters = true)
    public static final class Entry {
        private final UUID uuid;
        private long entityId;
        private CharSequence name;
        private String xuid;
        private String platformChatId;

        /**
         * @since v388
         */
        private int buildPlatform;

        private SerializedSkin skin;

        /**
         * @since v388
         */
        private boolean teacher;

        /**
         * @since v388
         */
        private boolean host;

        /**
         * @since v390
         */
        private boolean trustedSkin;

        private boolean subClient;
        private Color color;

        public String getName() {
            return getName(String.class);
        }

        public <T extends CharSequence> T getName(Class<T> type) {
            return type.cast(name);
        }
    }

    @Override
    public PlayerListPacket clone() {
        try {
            return (PlayerListPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
