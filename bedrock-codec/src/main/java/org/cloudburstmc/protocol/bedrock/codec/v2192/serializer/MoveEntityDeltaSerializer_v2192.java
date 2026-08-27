package org.cloudburstmc.protocol.bedrock.codec.v2192.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.MoveEntityDeltaSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityDeltaPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class MoveEntityDeltaSerializer_v2192 extends MoveEntityDeltaSerializer_v2168 {

    public static final MoveEntityDeltaSerializer_v2192 INSTANCE = new MoveEntityDeltaSerializer_v2192();

    protected MoveEntityDeltaSerializer_v2192() {
        super();
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityDeltaPacket packet) {
        super.serialize(buffer, helper, packet);
        VarInts.writeUnsignedLong(buffer, packet.getTicks());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityDeltaPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setTicks(VarInts.readUnsignedLong(buffer));
    }
}
