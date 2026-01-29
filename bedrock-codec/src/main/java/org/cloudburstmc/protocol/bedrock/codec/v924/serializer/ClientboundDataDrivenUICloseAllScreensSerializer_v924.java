package org.cloudburstmc.protocol.bedrock.codec.v924.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundDataDrivenUICloseAllScreensPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundDataDrivenUICloseAllScreensSerializer_v924 implements BedrockPacketSerializer<ClientboundDataDrivenUICloseAllScreensPacket> {

    public static final ClientboundDataDrivenUICloseAllScreensSerializer_v924 INSTANCE = new ClientboundDataDrivenUICloseAllScreensSerializer_v924();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundDataDrivenUICloseAllScreensPacket packet) {

    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundDataDrivenUICloseAllScreensPacket packet) {

    }
}
