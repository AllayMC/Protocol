package org.cloudburstmc.protocol.bedrock.codec.v407.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.EnchantData;
import org.cloudburstmc.protocol.bedrock.data.inventory.EnchantOptionData;
import org.cloudburstmc.protocol.bedrock.packet.PlayerEnchantOptionsPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerEnchantOptionsSerializer_v407 implements BedrockPacketSerializer<PlayerEnchantOptionsPacket> {

    public static final PlayerEnchantOptionsSerializer_v407 INSTANCE = new PlayerEnchantOptionsSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerEnchantOptionsPacket packet) {
        helper.writeArray(buffer, packet.getOptions(), this::writeOption);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerEnchantOptionsPacket packet) {
        helper.readArray(buffer, packet.getOptions(), this::readOption);
    }

    protected void writeOption(ByteBuf buffer, BedrockCodecHelper helper, EnchantOptionData option) {
        VarInts.writeUnsignedInt(buffer, option.cost());
        buffer.writeIntLE(option.primarySlot());
        helper.writeArray(buffer, option.enchants0(), this::serializeEnchant);
        helper.writeArray(buffer, option.enchants1(), this::serializeEnchant);
        helper.writeArray(buffer, option.enchants2(), this::serializeEnchant);
        helper.writeString(buffer, option.enchantName());
        VarInts.writeUnsignedInt(buffer, option.enchantNetId());
    }

    protected EnchantOptionData readOption(ByteBuf buffer, BedrockCodecHelper helper) {
        int cost = VarInts.readUnsignedInt(buffer);
        int primarySlot = buffer.readIntLE();
        List<EnchantData> enchants1 = new ObjectArrayList<>();
        helper.readArray(buffer, enchants1, this::deserializeEnchant);
        List<EnchantData> enchants2 = new ObjectArrayList<>();
        helper.readArray(buffer, enchants2, this::deserializeEnchant);
        List<EnchantData> enchants3 = new ObjectArrayList<>();
        helper.readArray(buffer, enchants3, this::deserializeEnchant);
        String enchantName = helper.readString(buffer);
        int enchantNetId = VarInts.readUnsignedInt(buffer);
        return new EnchantOptionData(cost, primarySlot, enchants1, enchants2, enchants3, enchantName, enchantNetId);
    }

    protected void serializeEnchant(ByteBuf buffer, EnchantData enchant) {
        buffer.writeByte(enchant.type());
        buffer.writeByte(enchant.level());
    }

    protected EnchantData deserializeEnchant(ByteBuf buffer) {
        int type = buffer.readUnsignedByte();
        int level = buffer.readUnsignedByte();
        return new EnchantData(type, level);
    }
}
