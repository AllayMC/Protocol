package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthPayload;

/**
 * Sent when a sub-client joins the server while another client is already connected to it. The
 * packet is sent as a result of split-screen game play, and allows up to four players to play using
 * the same network connection. After an initial Login packet from the 'main' client, each sub-
 * client that connects sends a SubClientLogin to request their own login.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SubClientLoginPacket implements BedrockPacket {
    /**
     * The authenticated identity payload for the joining sub-client.
     */
    private AuthPayload authPayload;
    /**
     * The client-signed JWT chain supplied alongside the login request.
     */
    private String clientJwt;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SUB_CLIENT_LOGIN;
    }

    @Override
    public SubClientLoginPacket clone() {
        try {
            return (SubClientLoginPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
