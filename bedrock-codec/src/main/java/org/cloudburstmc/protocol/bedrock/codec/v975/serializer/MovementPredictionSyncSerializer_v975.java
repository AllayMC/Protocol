package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.MovementPredictionSyncSerializer_v786;
import org.cloudburstmc.protocol.bedrock.packet.MovementPredictionSyncPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovementPredictionSyncSerializer_v975 extends MovementPredictionSyncSerializer_v786 {
    public static final MovementPredictionSyncSerializer_v975 INSTANCE = new MovementPredictionSyncSerializer_v975();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MovementPredictionSyncPacket packet) {
        this.serializeBeforeRuntimeEntityId(buffer, helper, packet);
        this.serializeMovementModifiers(buffer, packet);
        this.serializeRuntimeEntityId(buffer, packet);
        this.serializeFlying(buffer, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MovementPredictionSyncPacket packet) {
        this.deserializeBeforeRuntimeEntityId(buffer, helper, packet);
        this.deserializeMovementModifiers(buffer, packet);
        this.deserializeRuntimeEntityId(buffer, packet);
        this.deserializeFlying(buffer, packet);
    }

    protected void serializeMovementModifiers(ByteBuf buffer, MovementPredictionSyncPacket packet) {
        buffer.writeFloatLE(packet.getFrictionModifier());
        buffer.writeFloatLE(packet.getBounciness());
        buffer.writeFloatLE(packet.getAirDragModifier());
    }

    protected void deserializeMovementModifiers(ByteBuf buffer, MovementPredictionSyncPacket packet) {
        packet.setFrictionModifier(buffer.readFloatLE());
        packet.setBounciness(buffer.readFloatLE());
        packet.setAirDragModifier(buffer.readFloatLE());
    }

    @Override
    protected void serializeRuntimeEntityId(ByteBuf buffer, MovementPredictionSyncPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
    }

    @Override
    protected void deserializeRuntimeEntityId(ByteBuf buffer, MovementPredictionSyncPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
    }
}
