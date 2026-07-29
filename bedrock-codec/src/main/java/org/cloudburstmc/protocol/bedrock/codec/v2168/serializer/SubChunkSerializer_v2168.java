package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v818.serializer.SubChunkSerializer_v818;
import org.cloudburstmc.protocol.bedrock.data.HeightMapDataType;
import org.cloudburstmc.protocol.bedrock.data.SubChunkData;
import org.cloudburstmc.protocol.bedrock.data.SubChunkRequestResult;
import org.cloudburstmc.protocol.bedrock.packet.SubChunkPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class SubChunkSerializer_v2168 extends SubChunkSerializer_v818 {

    public static final SubChunkSerializer_v2168 INSTANCE = new SubChunkSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        buffer.writeBoolean(packet.isCacheEnabled());
        VarInts.writeInt(buffer, packet.getDimension());

        buffer.writeIntLE(packet.getCenterPosition().getX());
        buffer.writeIntLE(packet.getCenterPosition().getY());
        buffer.writeIntLE(packet.getCenterPosition().getZ());

        helper.writeArray(buffer, packet.getSubChunks(), (buf, h, subChunk) -> this.serializeSubChunk(buf, h, packet, subChunk));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        packet.setCacheEnabled(buffer.readBoolean());
        packet.setDimension(VarInts.readInt(buffer));

        packet.setCenterPosition(Vector3i.from(buffer.readIntLE(), buffer.readIntLE(), buffer.readIntLE()));

        helper.readArray(buffer, packet.getSubChunks(), (buf, h) -> this.deserializeSubChunk(buf, h, packet));
    }

    @Override
    protected void serializeSubChunk(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet, SubChunkData subChunk) {
        this.writeSubChunkOffset(buffer, subChunk.getPosition());
        buffer.writeByte(subChunk.getResult().ordinal());
        helper.writeOptionalNull(buffer, subChunk.getData(), helper::writeByteBuf);
        writeHeightMapData(buffer, helper, subChunk);
        helper.writeOptionalNull(buffer, subChunk.getBlobId(), ByteBuf::writeLongLE);
    }

    @Override
    protected SubChunkData deserializeSubChunk(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        SubChunkData subChunk = new SubChunkData();
        subChunk.setPosition(this.readSubChunkOffset(buffer));
        subChunk.setResult(SubChunkRequestResult.values()[buffer.readUnsignedByte()]);
        subChunk.setData(helper.readOptional(buffer, null, helper::readByteBuf));
        readHeightMapData(buffer, helper, subChunk);
        subChunk.setBlobId(helper.readOptional(buffer, null, ByteBuf::readLongLE));
        return subChunk;
    }

    private void writeHeightMapData(ByteBuf buffer, BedrockCodecHelper helper, SubChunkData subChunk) {
        buffer.writeByte(subChunk.getHeightMapType().ordinal());
        helper.writeOptional(buffer, o-> subChunk.getHeightMapData() != null, subChunk.getHeightMapData(), (buf, toWrite) -> {
            ByteBuf heightMapBuf = subChunk.getHeightMapData();
            buf.writeBytes(heightMapBuf, heightMapBuf.readerIndex(), HEIGHT_MAP_LENGTH);
        });
        buffer.writeByte(subChunk.getRenderHeightMapType().ordinal());
        helper.writeOptional(buffer, o-> subChunk.getRenderHeightMapData() != null, subChunk.getRenderHeightMapData(), (buf, toWrite) -> {
            ByteBuf renderHeightMapBuf = subChunk.getRenderHeightMapData();
            buf.writeBytes(renderHeightMapBuf, renderHeightMapBuf.readerIndex(), HEIGHT_MAP_LENGTH);
        });
    }

    private void readHeightMapData(ByteBuf buffer, BedrockCodecHelper helper, SubChunkData subChunk) {
        subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readUnsignedByte()]);
        subChunk.setHeightMapData(helper.readOptional(buffer, null, buf-> buf.readRetainedSlice(HEIGHT_MAP_LENGTH)));
        subChunk.setRenderHeightMapType(HeightMapDataType.values()[buffer.readUnsignedByte()]);
        subChunk.setRenderHeightMapData(helper.readOptional(buffer, null, buf-> buf.readRetainedSlice(HEIGHT_MAP_LENGTH)));
    }
}
