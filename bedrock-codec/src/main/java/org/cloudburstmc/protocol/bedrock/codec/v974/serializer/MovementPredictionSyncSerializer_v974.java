package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.MovementPredictionSyncPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovementPredictionSyncSerializer_v974 implements BedrockPacketSerializer<MovementPredictionSyncPacket> {
    public static final MovementPredictionSyncSerializer_v974 INSTANCE = new MovementPredictionSyncSerializer_v974();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MovementPredictionSyncPacket packet) {
        helper.writeLargeVarIntFlags(buffer, packet.getFlags(), EntityFlag.class);
        helper.writeVector3f(buffer, packet.getBoundingBox());
        buffer.writeFloatLE(packet.getSpeed());
        buffer.writeFloatLE(packet.getUnderwaterSpeed());
        buffer.writeFloatLE(packet.getLavaSpeed());
        buffer.writeFloatLE(packet.getJumpStrength());
        buffer.writeFloatLE(packet.getHealth());
        buffer.writeFloatLE(packet.getHunger());
        buffer.writeFloatLE(packet.getFrictionModifier());
        buffer.writeFloatLE(packet.getBounciness());
        buffer.writeFloatLE(packet.getAirDragModifier());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeBoolean(packet.isFlying());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MovementPredictionSyncPacket packet) {
        helper.readLargeVarIntFlags(buffer, packet.getFlags(), EntityFlag.class);
        packet.setBoundingBox(helper.readVector3f(buffer));
        packet.setSpeed(buffer.readFloatLE());
        packet.setUnderwaterSpeed(buffer.readFloatLE());
        packet.setLavaSpeed(buffer.readFloatLE());
        packet.setJumpStrength(buffer.readFloatLE());
        packet.setHealth(buffer.readFloatLE());
        packet.setHunger(buffer.readFloatLE());
        packet.setFrictionModifier(buffer.readFloatLE());
        packet.setBounciness(buffer.readFloatLE());
        packet.setAirDragModifier(buffer.readFloatLE());
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setFlying(buffer.readBoolean());
    }
}
