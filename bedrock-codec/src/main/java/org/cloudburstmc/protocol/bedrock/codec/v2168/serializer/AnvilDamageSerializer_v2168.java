package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AnvilDamagePacket;

public class AnvilDamageSerializer_v2168 implements BedrockPacketSerializer<AnvilDamagePacket> {

    public static final AnvilDamageSerializer_v2168 INSTANCE = new AnvilDamageSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AnvilDamagePacket packet) {
        helper.writeBlockPosition(buffer, packet.getPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AnvilDamagePacket packet) {
        packet.setPosition(helper.readBlockPosition(buffer));
    }
}
