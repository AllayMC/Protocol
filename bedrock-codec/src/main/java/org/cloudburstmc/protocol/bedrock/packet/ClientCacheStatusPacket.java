package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to the server at the start of the game. It is sent to let the server know if
 * it supports the client-side blob cache. Clients such as Nintendo Switch do not support the cache,
 * and attempting to use it anyway will fail.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientCacheStatusPacket implements BedrockPacket {
    /**
     * Whether the client supports the blob cache protocol.
     */
    private boolean supported;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENT_CACHE_STATUS;
    }

    @Override
    public ClientCacheStatusPacket clone() {
        try {
            return (ClientCacheStatusPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
