package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerPresenceInfoPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerPresenceInfoSerializer_v974 implements BedrockPacketSerializer<ServerPresenceInfoPacket> {
    public static final ServerPresenceInfoSerializer_v974 INSTANCE = new ServerPresenceInfoSerializer_v974();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerPresenceInfoPacket packet) {
        helper.writeOptionalNull(buffer, packet.getPresenceConfiguration(), this::writeConfiguration);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerPresenceInfoPacket packet) {
        packet.setPresenceConfiguration(helper.readOptional(buffer, null, this::readConfiguration));
    }

    private void writeConfiguration(ByteBuf buffer, BedrockCodecHelper helper,
                                    ServerPresenceInfoPacket.PresenceConfiguration configuration) {
        helper.writeString(buffer, configuration.getExperienceName());
        helper.writeString(buffer, configuration.getWorldName());
    }

    private ServerPresenceInfoPacket.PresenceConfiguration readConfiguration(ByteBuf buffer, BedrockCodecHelper helper) {
        ServerPresenceInfoPacket.PresenceConfiguration configuration = new ServerPresenceInfoPacket.PresenceConfiguration();
        configuration.setExperienceName(helper.readString(buffer));
        configuration.setWorldName(helper.readString(buffer));
        return configuration;
    }
}
