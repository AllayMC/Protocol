package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v800.serializer.PlayerLocationSerializer_v800;
import org.cloudburstmc.protocol.bedrock.packet.PlayerLocationPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class PlayerLocationSerializer_v2168 extends PlayerLocationSerializer_v800 {

    public static final PlayerLocationSerializer_v2168 INSTANCE = new PlayerLocationSerializer_v2168();
    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerLocationPacket packet) {
        VarInts.writeLong(buffer, packet.getTargetEntityId());
        VarInts.writeUnsignedInt(buffer, packet.getType().ordinal());

        VarInts.writeInt(buffer, packet.getType().ordinal());

        if (packet.getType() == PlayerLocationPacket.Type.COORDINATES) {
            helper.writeVector3f(buffer, packet.getPosition());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerLocationPacket packet) {
        packet.setTargetEntityId(VarInts.readLong(buffer));
        packet.setType(VALUES[VarInts.readUnsignedInt(buffer)]);

        VarInts.readInt(buffer);

        if (packet.getType() == PlayerLocationPacket.Type.COORDINATES) {
            packet.setPosition(helper.readVector3f(buffer));
        }
    }
}
