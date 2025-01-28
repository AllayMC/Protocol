package org.cloudburstmc.protocol.bedrock.codec.v776.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventGenericPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;
import org.cloudburstmc.protocol.common.util.VarInts;

public class LevelEventGenericSerializer_v776 extends LevelEventGenericSerializer_v361 {

    public LevelEventGenericSerializer_v776(TypeMap<LevelEventType> typeMap) {
        super(typeMap);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelEventGenericPacket packet) {
        VarInts.writeInt(buffer, typeMap.getId(packet.getType()));
        buffer.writeByte(packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelEventGenericPacket packet) {
        int eventId = VarInts.readInt(buffer);
        packet.setType(typeMap.getType(eventId));
        packet.setData(buffer.readByte());
    }
}
