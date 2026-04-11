package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerStoreInfoPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerStoreInfoSerializer_v974 implements BedrockPacketSerializer<ServerStoreInfoPacket> {
    public static final ServerStoreInfoSerializer_v974 INSTANCE = new ServerStoreInfoSerializer_v974();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerStoreInfoPacket packet) {
        helper.writeOptionalNull(buffer, packet.getClientStoreEntryPointConfiguration(), this::writeConfiguration);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerStoreInfoPacket packet) {
        packet.setClientStoreEntryPointConfiguration(helper.readOptional(buffer, null, this::readConfiguration));
    }

    private void writeConfiguration(ByteBuf buffer, BedrockCodecHelper helper,
                                    ServerStoreInfoPacket.ClientStoreEntryPointConfiguration configuration) {
        helper.writeString(buffer, configuration.getStoreId());
        helper.writeString(buffer, configuration.getStoreName());
    }

    private ServerStoreInfoPacket.ClientStoreEntryPointConfiguration readConfiguration(ByteBuf buffer, BedrockCodecHelper helper) {
        ServerStoreInfoPacket.ClientStoreEntryPointConfiguration configuration =
                new ServerStoreInfoPacket.ClientStoreEntryPointConfiguration();
        configuration.setStoreId(helper.readString(buffer));
        configuration.setStoreName(helper.readString(buffer));
        return configuration;
    }
}
