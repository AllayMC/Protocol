package org.cloudburstmc.protocol.bedrock.codec.v776.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.UpdateEquipSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.UpdateEquipPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateEquipSerializer_v776 extends UpdateEquipSerializer_v291 {
    public static final UpdateEquipSerializer_v776 INSTANCE = new UpdateEquipSerializer_v776();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateEquipPacket packet) {
        buffer.writeByte(packet.getWindowId());
        buffer.writeByte(packet.getWindowType());
        VarInts.writeInt(buffer, packet.getSize());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        buffer.writeByte(packet.getAByte());
        helper.writeString(buffer, packet.getAString());
        buffer.writeByte(packet.getAByte2());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateEquipPacket packet) {
        packet.setWindowId(buffer.readUnsignedByte());
        packet.setWindowType(buffer.readUnsignedByte());
        packet.setSize(VarInts.readInt(buffer));
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setAByte(buffer.readByte());
        packet.setAString(helper.readString(buffer));
        packet.setAByte2(buffer.readByte());
    }
}
