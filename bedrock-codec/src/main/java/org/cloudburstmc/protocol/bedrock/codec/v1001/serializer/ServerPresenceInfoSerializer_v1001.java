package org.cloudburstmc.protocol.bedrock.codec.v1001.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.ServerPresenceInfoSerializer_v975;
import org.cloudburstmc.protocol.bedrock.packet.ServerPresenceInfoPacket;

public class ServerPresenceInfoSerializer_v1001 extends ServerPresenceInfoSerializer_v975 {

    public static final ServerPresenceInfoSerializer_v1001 INSTANCE = new ServerPresenceInfoSerializer_v1001();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerPresenceInfoPacket packet) {
        helper.writeOptionalNull(buffer, packet.getPresenceConfiguration(), (buf, h, configuration) -> {
            h.writeOptionalNull(buf, configuration.getExperienceName(), h::writeString);
            h.writeOptionalNull(buf, configuration.getWorldName(), h::writeString);
            h.writeString(buf, configuration.getRichPresenceId());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerPresenceInfoPacket packet) {
        packet.setPresenceConfiguration(helper.readOptional(buffer, null, (buf, h) -> {
            ServerPresenceInfoPacket.PresenceConfiguration configuration = new ServerPresenceInfoPacket.PresenceConfiguration();
            configuration.setExperienceName(h.readOptional(buf, null, h::readString));
            configuration.setWorldName(h.readOptional(buf, null, h::readString));
            configuration.setRichPresenceId(h.readString(buf));
            return configuration;
        }));
    }
}
