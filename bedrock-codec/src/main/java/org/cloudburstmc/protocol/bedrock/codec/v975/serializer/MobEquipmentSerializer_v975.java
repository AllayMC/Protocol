package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.MobEquipmentSerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.MobEquipmentPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobEquipmentSerializer_v975 extends MobEquipmentSerializer_v291 {
    public static final MobEquipmentSerializer_v975 INSTANCE = new MobEquipmentSerializer_v975();

    @Override
    protected void serializeItem(ByteBuf buffer, BedrockCodecHelper helper, MobEquipmentPacket packet) {
        helper.writeNetItemDescriptor(buffer, packet.getItem());
    }

    @Override
    protected void deserializeItem(ByteBuf buffer, BedrockCodecHelper helper, MobEquipmentPacket packet) {
        packet.setItem(helper.readNetItemDescriptor(buffer));
    }

    @Override
    protected void serializeSlots(ByteBuf buffer, MobEquipmentPacket packet) {
        buffer.writeByte(packet.getInventorySlot());
        buffer.writeByte(packet.getHotbarSlot());
        buffer.writeByte(packet.getContainerId());
    }

    @Override
    protected void deserializeSlots(ByteBuf buffer, MobEquipmentPacket packet) {
        packet.setInventorySlot(buffer.readUnsignedByte());
        packet.setHotbarSlot(buffer.readUnsignedByte());
        packet.setContainerId(buffer.readByte());
    }
}
