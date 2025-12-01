package org.cloudburstmc.protocol.bedrock.codec.v897.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v748.serializer.MobEffectSerializer_v748;
import org.cloudburstmc.protocol.bedrock.packet.MobEffectPacket;

public class MobEffectSerializer_v897 extends MobEffectSerializer_v748 {

    public static final MobEffectSerializer_v897 INSTANCE = new MobEffectSerializer_v897();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MobEffectPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isAmbient());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MobEffectPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setAmbient(buffer.readBoolean());
    }
}
