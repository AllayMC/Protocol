package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongList;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v649.serializer.LevelChunkSerializer_v649;
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class LevelChunkSerializer_v2168 extends LevelChunkSerializer_v649 {

    public static final LevelChunkSerializer_v2168 INSTANCE = new LevelChunkSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelChunkPacket packet) {
        this.writeChunkLocation(buffer, packet);

        VarInts.writeUnsignedInt(buffer, packet.getSubChunksLength());

        helper.writeOptional(buffer, o-> packet.isRequestSubChunks(), packet.getSubChunkLimit(), VarInts::writeInt);

        buffer.writeBoolean(packet.isCachingEnabled());

        LongList blobIds = packet.getBlobIds();
        VarInts.writeUnsignedInt(buffer, blobIds.size());

        for (long blobId : blobIds) {
            buffer.writeLongLE(blobId);
        }

        helper.writeByteBuf(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelChunkPacket packet) {
        this.readChunkLocation(buffer, packet);

        packet.setSubChunksLength(VarInts.readUnsignedInt(buffer));

        packet.setSubChunkLimit(helper.readOptional(buffer, 0, buffer1 -> {
            packet.setRequestSubChunks(true);
            return VarInts.readInt(buffer1);
        }));

        packet.setCachingEnabled(buffer.readBoolean());

        LongList blobIds = packet.getBlobIds();
        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            blobIds.add(buffer.readLongLE());
        }

        packet.setData(helper.readByteBuf(buffer));
    }
}
