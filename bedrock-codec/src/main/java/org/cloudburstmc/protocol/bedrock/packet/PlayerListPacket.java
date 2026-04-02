package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.BuildPlatform;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;

import java.awt.*;
import java.util.List;
import java.util.UUID;

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
    /**
     * A list of all player list entries that should be added/removed from the player list,
     * depending on the ActionType set.
     */
    private final List<Entry> entries = new ObjectArrayList<>();
    /**
     * The action to execute upon the player list. The entries that follow specify which entries are
     * added or removed from the player list.
     */
    private Action action;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_LIST;
    }

    public enum Action {
        /**
         * Add the supplied entries to the client-side player list.
         */
        ADD,
        /**
         * Remove the supplied entries from the client-side player list.
         */
        REMOVE
    }

    @Data
    @ToString(doNotUseGetters = true)
    @EqualsAndHashCode(doNotUseGetters = true)
    public static final class Entry {
        /**
         * The player's UUID as sent during login.
         */
        private final UUID uuid;
        /**
         * The player's unique entity ID.
         */
        private long entityId;
        /**
         * The display name shown in the client-side player list.
         */
        private String name;
        /**
         * The player's Xbox Live user ID.
         */
        private String xuid;
        /**
         * A platform-specific chat identifier, typically only populated on some consoles.
         */
        private String platformChatId;
        /**
         * The skin that should be cached for this player list entry.
         */
        private SerializedSkin skin;
        /**
         * The platform reported by the player in the login chain.
         *
         * @since v388
         */
        private BuildPlatform buildPlatform = BuildPlatform.UNKNOWN;
        /**
         * Whether the player is marked as a teacher in Education Edition.
         *
         * @since v388
         */
        private boolean teacher;
        /**
         * Whether the player is the host of the world/session.
         *
         * @since v388
         */
        private boolean host;
        /**
         * Whether the skin attached to this entry is trusted.
         *
         * @since v390
         */
        private boolean trustedSkin;
        /**
         * Whether this entry represents a sub-client rather than the primary player.
         *
         * @since v649
         */
        private boolean subClient;
        /**
         * The UI colour associated with the player, currently used by features such as the locator
         * bar.
         *
         * @since v800
         */
        private Color color;
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
