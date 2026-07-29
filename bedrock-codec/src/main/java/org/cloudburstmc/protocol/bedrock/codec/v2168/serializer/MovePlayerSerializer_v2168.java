package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.MovePlayerSerializer_v419;
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class MovePlayerSerializer_v2168 extends MovePlayerSerializer_v419 {

    public static final MovePlayerSerializer_v2168 INSTANCE = new MovePlayerSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MovePlayerPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getRotation());
        buffer.writeByte(packet.getMode().ordinal());
        buffer.writeBoolean(packet.isOnGround());
        VarInts.writeUnsignedLong(buffer, packet.getRidingRuntimeEntityId());

        helper.writeOptional(buffer, value -> value.getMode() == MovePlayerPacket.Mode.TELEPORT, packet, (buf, value) -> {
            buf.writeIntLE(value.getTeleportationCause().ordinal());
            buf.writeIntLE(value.getEntityType());
        });

        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MovePlayerPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setPosition(helper.readVector3f(buffer));
        packet.setRotation(helper.readVector3f(buffer));
        packet.setMode(MovePlayerPacket.Mode.values()[buffer.readUnsignedByte()]);
        packet.setOnGround(buffer.readBoolean());
        packet.setRidingRuntimeEntityId(VarInts.readUnsignedLong(buffer));

        if (buffer.readBoolean()) {
            packet.setTeleportationCause(MovePlayerPacket.TeleportationCause.byId(buffer.readIntLE()));
            packet.setEntityType(buffer.readIntLE());
        }

        packet.setTick(VarInts.readUnsignedLong(buffer));
    }
}
