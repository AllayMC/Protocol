package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v729.serializer.TransferSerializer_v729;
import org.cloudburstmc.protocol.bedrock.packet.TransferPacket;

public class TransferSerializer_v2168 extends TransferSerializer_v729 {

    public static final TransferSerializer_v2168 INSTANCE = new TransferSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TransferPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getGatheringsConfigurationJoinInfo(), helper::writeGatheringsConfiguration);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TransferPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setGatheringsConfigurationJoinInfo(helper.readOptional(buffer, null, helper::readGatheringsConfiguration));
    }
}
