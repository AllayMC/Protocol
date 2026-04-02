package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client in response to {@link ServerToClientHandshakePacket}. It is the first
 * encrypted packet in the login handshake and confirms that client-side encryption was initialized
 * successfully.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientToServerHandshakePacket implements BedrockPacket {

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENT_TO_SERVER_HANDSHAKE;
    }

    @Override
    public ClientToServerHandshakePacket clone() {
        try {
            return (ClientToServerHandshakePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
