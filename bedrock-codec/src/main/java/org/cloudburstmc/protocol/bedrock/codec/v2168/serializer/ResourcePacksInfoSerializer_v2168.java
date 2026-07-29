package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v818.serializer.ResourcePacksInfoSerializer_v818;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;

import java.util.Collection;

public class ResourcePacksInfoSerializer_v2168 extends ResourcePacksInfoSerializer_v818 {

    public static final ResourcePacksInfoSerializer_v2168 INSTANCE = new ResourcePacksInfoSerializer_v2168();

    @Override
    protected void readPacks(ByteBuf buffer, Collection<ResourcePacksInfoPacket.Entry> array, BedrockCodecHelper helper,
                             boolean resource) {
        helper.readArray(buffer, array, (buf, h) -> this.readEntry(buf, h, resource));
    }

    @Override
    protected void writePacks(ByteBuf buffer, Collection<ResourcePacksInfoPacket.Entry> array, BedrockCodecHelper helper,
                              boolean resource) {
        helper.writeArray(buffer, array, (buf, h, entry) -> this.writeEntry(buf, h, entry, resource));
    }
}
