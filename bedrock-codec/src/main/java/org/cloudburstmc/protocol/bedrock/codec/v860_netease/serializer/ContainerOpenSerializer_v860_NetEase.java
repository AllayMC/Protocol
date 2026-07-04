package org.cloudburstmc.protocol.bedrock.codec.v860_netease.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.ContainerOpenSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.ContainerOpenPacket;

public class ContainerOpenSerializer_v860_NetEase extends ContainerOpenSerializer_v291 {
    public static final ContainerOpenSerializer_v860_NetEase INSTANCE = new ContainerOpenSerializer_v860_NetEase();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ContainerOpenPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isIgnoreBlock());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ContainerOpenPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setIgnoreBlock(buffer.readBoolean());
    }
}
