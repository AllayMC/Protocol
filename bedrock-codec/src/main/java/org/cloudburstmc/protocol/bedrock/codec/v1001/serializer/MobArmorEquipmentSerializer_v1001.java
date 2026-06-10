package org.cloudburstmc.protocol.bedrock.codec.v1001.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.MobArmorEquipmentSerializer_v712;
import org.cloudburstmc.protocol.bedrock.packet.MobArmorEquipmentPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class MobArmorEquipmentSerializer_v1001 extends MobArmorEquipmentSerializer_v712 {

    public static final MobArmorEquipmentSerializer_v1001 INSTANCE = new MobArmorEquipmentSerializer_v1001();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MobArmorEquipmentPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeNetItemDescriptor(buffer, packet.getHelmet());
        helper.writeNetItemDescriptor(buffer, packet.getChestplate());
        helper.writeNetItemDescriptor(buffer, packet.getLeggings());
        helper.writeNetItemDescriptor(buffer, packet.getBoots());
        helper.writeNetItemDescriptor(buffer, packet.getBody());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MobArmorEquipmentPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setHelmet(helper.readNetItemDescriptor(buffer));
        packet.setChestplate(helper.readNetItemDescriptor(buffer));
        packet.setLeggings(helper.readNetItemDescriptor(buffer));
        packet.setBoots(helper.readNetItemDescriptor(buffer));
        packet.setBody(helper.readNetItemDescriptor(buffer));
    }
}
