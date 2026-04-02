package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthPayload;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent when the client initially tries to join the server. It is the first packet sent and contains
 * information specific to the player.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class LoginPacket implements BedrockPacket {
    /**
     * The protocol version reported by the client. Modern clients also send a protocol version in
     * {@link RequestNetworkSettingsPacket}, so servers should not rely on this field alone.
     */
    private int protocolVersion;
    /**
     * The parsed connection request payload. It contains the authenticated chain data and the
     * client data JWT, including the client's public key used to start encryption.
     */
    private AuthPayload authPayload;
    /**
     * The raw client data JWT signed by the client itself. It may be modified by the client and
     * should therefore not be trusted on its own.
     */
    private String clientJwt;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LOGIN;
    }

    @Override
    public LoginPacket clone() {
        try {
            return (LoginPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
