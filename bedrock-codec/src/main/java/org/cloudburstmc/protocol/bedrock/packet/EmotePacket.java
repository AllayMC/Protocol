package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.EmoteFlag;

import java.util.EnumSet;
import java.util.Set;

/**
 * Sent by both the server and the client. When the client sends an emote, it sends this packet to
 * the server, after which the server will broadcast the packet to other players online.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EmotePacket implements BedrockPacket {
    /**
     * The entity that sent the emote. When a player sends this packet, it has this field set as its
     * own entity runtime ID.
     */
    private long runtimeEntityId;
    /**
     * The identifier of the emote animation to play.
     */
    private String emoteId;
    /**
     * A combination of flags that change the way the Emote packet operates. When the server sends
     * this packet to other players, EmoteFlagServerSide must be present.
     */
    private final Set<EmoteFlag> flags = EnumSet.noneOf(EmoteFlag.class);
    /**
     * The Xbox User ID of the player that sent the emote. It is only set when the emote is used by
     * a player that is authenticated with Xbox Live.
     *
     * @since v589
     */
    private String xuid;
    /**
     * An identifier only set for particular platforms when using an emote (presumably only for
     * Nintendo Switch). It is otherwise an empty string, and is used to decide which players are
     * able to emote with each other.
     *
     * @since v589
     */
    private String platformId;
    /**
     * The number of ticks that the emote lasts for.
     *
     * @since v729
     */
    private int emoteDuration;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EMOTE;
    }

    @Override
    public EmotePacket clone() {
        try {
            return (EmotePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
