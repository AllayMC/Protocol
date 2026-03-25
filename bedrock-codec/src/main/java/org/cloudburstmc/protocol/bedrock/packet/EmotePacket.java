package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.EmoteFlag;
import org.cloudburstmc.protocol.common.PacketSignal;
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
    private long runtimeEntityId;
    /**
     * @since v589
     */
    private String xuid;
    /**
     * @since v589
     */
    private String platformId;

    private String emoteId;
    private final Set<EmoteFlag> flags = EnumSet.noneOf(EmoteFlag.class);
    /**
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
