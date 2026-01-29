package org.cloudburstmc.protocol.bedrock.codec.v924.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.SerializableVoxelShape;
import org.cloudburstmc.protocol.bedrock.packet.VoxelShapesPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class VoxelShapesSerializer_v924 implements BedrockPacketSerializer<VoxelShapesPacket> {

    public static final VoxelShapesSerializer_v924 INSTANCE = new VoxelShapesSerializer_v924();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, VoxelShapesPacket packet) {
        helper.writeArray(buffer, packet.getShapes(), (buf, shape) -> {
            helper.writeArray(buf, shape.getCells(), (buf2, frame) -> {
                buf2.writeByte(frame.getXSize());
                buf2.writeByte(frame.getYSize());
                buf2.writeByte(frame.getZSize());

                helper.writeArray(buf2, frame.getStorage(), (buf3, value) -> buf3.writeByte(value));
            });

            helper.writeArray(buf, shape.getXCoordinates(), ByteBuf::writeFloatLE);
            helper.writeArray(buf, shape.getYCoordinates(), ByteBuf::writeFloatLE);
            helper.writeArray(buf, shape.getZCoordinates(), ByteBuf::writeFloatLE);
        });

        VarInts.writeUnsignedInt(buffer, packet.getNameMap().size());
        packet.getNameMap().forEach((k, v) -> {
            helper.writeString(buffer, k);
            buffer.getUnsignedShortLE(v);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, VoxelShapesPacket packet) {
        List<SerializableVoxelShape> shapes = new ArrayList<>();

        helper.readArray(buffer, shapes, (buf, h) -> {
            List<SerializableVoxelShape.SerializableCells> cells = new ArrayList<>();

            helper.readArray(buf, cells, buf2 -> {
                short xSize = buf2.readUnsignedByte();
                short ySize = buf2.readUnsignedByte();
                short zSize = buf2.readUnsignedByte();

                List<Short> storage = new ArrayList<>();
                helper.readArray(buf2, storage, (ByteBuf::readUnsignedByte));

                return new SerializableVoxelShape.SerializableCells(xSize, ySize, zSize, storage);
            });

            List<Float> xCoordinates = new ArrayList<>();
            helper.readArray(buf, xCoordinates, (ByteBuf::readFloatLE));

            List<Float> yCoordinates = new ArrayList<>();
            helper.readArray(buf, yCoordinates, (ByteBuf::readFloatLE));

            List<Float> zCoordinates = new ArrayList<>();
            helper.readArray(buf, zCoordinates, (ByteBuf::readFloatLE));

            return new SerializableVoxelShape(cells, xCoordinates, yCoordinates, zCoordinates);
        });

        packet.setShapes(shapes);

        Map<String, Integer> nameMap = new HashMap<>();

        int size = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < size; i++) {
            nameMap.put(helper.readString(buffer), buffer.readUnsignedShortLE());
        }

        packet.setNameMap(nameMap);
    }
}
