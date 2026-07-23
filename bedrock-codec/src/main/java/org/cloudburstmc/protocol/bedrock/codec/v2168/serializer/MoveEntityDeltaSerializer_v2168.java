package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.MoveEntityDeltaSerializer_v419;
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityDeltaPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import static org.cloudburstmc.protocol.bedrock.packet.MoveEntityDeltaPacket.Flag.*;

public class MoveEntityDeltaSerializer_v2168 extends MoveEntityDeltaSerializer_v419 {

    public static final MoveEntityDeltaSerializer_v2168 INSTANCE = new MoveEntityDeltaSerializer_v2168();

    protected MoveEntityDeltaSerializer_v2168() {
        super();
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityDeltaPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeOptional(buffer, f->f.contains(HAS_X), packet.getFlags(), (buf, h) -> buf.writeFloatLE(packet.getX()));
        helper.writeOptional(buffer, f->f.contains(HAS_Y), packet.getFlags(), (buf, h) -> buf.writeFloatLE(packet.getY()));
        helper.writeOptional(buffer, f->f.contains(HAS_Z), packet.getFlags(), (buf, h) -> buf.writeFloatLE(packet.getZ()));
        helper.writeOptional(buffer, f->f.contains(HAS_PITCH), packet.getFlags(), (buf, h) -> helper.writeByteAngle(buf, packet.getPitch()));
        helper.writeOptional(buffer, f->f.contains(HAS_YAW), packet.getFlags(), (buf, h) -> helper.writeByteAngle(buf, packet.getYaw()));
        helper.writeOptional(buffer, f->f.contains(HAS_HEAD_YAW), packet.getFlags(), (buf, h) -> helper.writeByteAngle(buf, packet.getHeadYaw()));
        buffer.writeBoolean(packet.isOnGround());
        buffer.writeBoolean(packet.isForceMove());
        buffer.writeBoolean(packet.isForceMoveLocalEntity());
        buffer.writeBoolean(packet.isForceCompletion());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MoveEntityDeltaPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setX(helper.readOptional(buffer, 0f, byteBuf -> {
            packet.getFlags().add(HAS_X);
            return byteBuf.readFloatLE();
        }));
        packet.setY(helper.readOptional(buffer, 0f, byteBuf -> {
            packet.getFlags().add(HAS_Y);
            return byteBuf.readFloatLE();
        }));
        packet.setZ(helper.readOptional(buffer, 0f, byteBuf -> {
            packet.getFlags().add(HAS_Z);
            return byteBuf.readFloatLE();
        }));
        packet.setPitch(helper.readOptional(buffer, 0f, byteBuf -> {
            packet.getFlags().add(HAS_PITCH);
            return helper.readByteAngle(byteBuf);
        }));
        packet.setYaw(helper.readOptional(buffer, 0f, byteBuf -> {
            packet.getFlags().add(HAS_YAW);
            return helper.readByteAngle(byteBuf);
        }));
        packet.setHeadYaw(helper.readOptional(buffer, 0f, byteBuf -> {
            packet.getFlags().add(HAS_HEAD_YAW);
            return helper.readByteAngle(byteBuf);
        }));
        packet.setOnGround(buffer.readBoolean());
        packet.setForceMove(buffer.readBoolean());
        packet.setForceMoveLocalEntity(buffer.readBoolean());
        packet.setForceCompletion(buffer.readBoolean());
    }
}
