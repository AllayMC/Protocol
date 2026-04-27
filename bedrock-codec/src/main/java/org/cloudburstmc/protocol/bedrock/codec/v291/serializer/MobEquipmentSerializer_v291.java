package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.MobEquipmentPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobEquipmentSerializer_v291 implements BedrockPacketSerializer<MobEquipmentPacket> {
    public static final MobEquipmentSerializer_v291 INSTANCE = new MobEquipmentSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MobEquipmentPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        this.serializeItem(buffer, helper, packet);
        this.serializeSlots(buffer, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MobEquipmentPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        this.deserializeItem(buffer, helper, packet);
        this.deserializeSlots(buffer, packet);
    }

    protected void serializeItem(ByteBuf buffer, BedrockCodecHelper helper, MobEquipmentPacket packet) {
        helper.writeItem(buffer, packet.getItem());
    }

    protected void deserializeItem(ByteBuf buffer, BedrockCodecHelper helper, MobEquipmentPacket packet) {
        packet.setItem(helper.readItem(buffer));
    }

    protected void serializeSlots(ByteBuf buffer, MobEquipmentPacket packet) {
        buffer.writeByte(packet.getInventorySlot());
        buffer.writeByte(packet.getHotbarSlot());
        buffer.writeByte(packet.getContainerId());
    }

    protected void deserializeSlots(ByteBuf buffer, MobEquipmentPacket packet) {
        packet.setInventorySlot(buffer.readUnsignedByte());
        packet.setHotbarSlot(buffer.readUnsignedByte());
        packet.setContainerId(buffer.readByte());
    }
}
