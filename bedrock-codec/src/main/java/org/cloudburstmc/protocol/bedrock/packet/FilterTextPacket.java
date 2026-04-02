package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by both the client and the server. The client sends the packet to the server to allow
 * the server to filter the text server-side. The server then responds with the same packet and the
 * safer version of the text.
 *
 * @deprecated This packet was deprecated in an unknown protocol version.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class FilterTextPacket implements BedrockPacket {
    /**
     * Either the text from the client or the safer version of the text sent by the server.
     */
    private String text;
    /**
     * FromServer indicates if the packet was sent by the server or not.
     */
    private boolean fromServer;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.FILTER_TEXT;
    }

    @Override
    public FilterTextPacket clone() {
        try {
            return (FilterTextPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
