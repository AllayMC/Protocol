package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.ResourcePackClientResponseSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import static org.cloudburstmc.protocol.bedrock.packet.ResourcePackClientResponsePacket.Status;

public class ResourcePackClientResponseSerializer_v2168 extends ResourcePackClientResponseSerializer_v291 {

    public static final ResourcePackClientResponseSerializer_v2168 INSTANCE = new ResourcePackClientResponseSerializer_v2168();

    private static final String[] RESPONSE_STATUS = {"cancel", "downloading", "downloadingfinished", "resourcepackstackfinished"};

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackClientResponsePacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getStatus().ordinal() - 1);

        helper.writeString(buffer, RESPONSE_STATUS[packet.getStatus().ordinal() - 1]);

        if (packet.getStatus() != Status.SEND_PACKS) {
            return;
        }

        helper.writeArray(buffer, packet.getPackIds(), helper::writeString);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ResourcePackClientResponsePacket packet) {
        packet.setStatus(Status.values()[VarInts.readUnsignedInt(buffer) + 1]);

        helper.readString(buffer); // type enum

        if (packet.getStatus() != Status.SEND_PACKS) {
            return;
        }

        helper.readArray(buffer, packet.getPackIds(), helper::readString);
    }
}
