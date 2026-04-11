package org.cloudburstmc.protocol.bedrock.codec.v944.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.EditorNetworkSerializer_v712;
import org.cloudburstmc.protocol.bedrock.packet.EditorNetworkPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditorNetworkSerializer_v944 extends EditorNetworkSerializer_v712 {
    public static final EditorNetworkSerializer_v944 INSTANCE = new EditorNetworkSerializer_v944();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, EditorNetworkPacket packet) {
        buffer.writeBoolean(packet.isRouteToManager());
        helper.writeString(buffer, packet.getRawVariantName());
        helper.writeString(buffer, packet.getRawVariantData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, EditorNetworkPacket packet) {
        packet.setRouteToManager(buffer.readBoolean());
        packet.setRawVariantName(helper.readString(buffer));
        packet.setRawVariantData(helper.readString(buffer));
    }
}
