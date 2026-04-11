package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to advertise optional store entry-point metadata.
 *
 * @since v974
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerStoreInfoPacket implements BedrockPacket {

    private ClientStoreEntryPointConfiguration clientStoreEntryPointConfiguration;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVER_STORE_INFO;
    }

    @Override
    public ServerStoreInfoPacket clone() {
        try {
            return (ServerStoreInfoPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Data
    @EqualsAndHashCode(doNotUseGetters = true)
    @ToString(doNotUseGetters = true)
    public static class ClientStoreEntryPointConfiguration {
        private String storeId;
        private String storeName;
    }
}
