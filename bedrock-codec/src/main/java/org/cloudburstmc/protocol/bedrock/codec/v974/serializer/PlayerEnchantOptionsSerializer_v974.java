package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.PlayerEnchantOptionsSerializer_v407;
import org.cloudburstmc.protocol.bedrock.data.inventory.EnchantData;
import org.cloudburstmc.protocol.bedrock.data.inventory.EnchantOptionData;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerEnchantOptionsSerializer_v974 extends PlayerEnchantOptionsSerializer_v407 {

    public static final PlayerEnchantOptionsSerializer_v974 INSTANCE = new PlayerEnchantOptionsSerializer_v974();

    @Override
    protected void writeOption(ByteBuf buffer, BedrockCodecHelper helper, EnchantOptionData option) {
        buffer.writeByte(option.cost());
        buffer.writeIntLE(option.primarySlot());
        helper.writeArray(buffer, option.enchants0(), this::serializeEnchant);
        helper.writeArray(buffer, option.enchants1(), this::serializeEnchant);
        helper.writeArray(buffer, option.enchants2(), this::serializeEnchant);
        helper.writeString(buffer, option.enchantName());
        VarInts.writeUnsignedInt(buffer, option.enchantNetId());
    }

    @Override
    protected EnchantOptionData readOption(ByteBuf buffer, BedrockCodecHelper helper) {
        int cost = buffer.readUnsignedByte();
        int primarySlot = buffer.readIntLE();
        List<EnchantData> enchants0 = new ObjectArrayList<>();
        helper.readArray(buffer, enchants0, this::deserializeEnchant);
        List<EnchantData> enchants1 = new ObjectArrayList<>();
        helper.readArray(buffer, enchants1, this::deserializeEnchant);
        List<EnchantData> enchants2 = new ObjectArrayList<>();
        helper.readArray(buffer, enchants2, this::deserializeEnchant);
        String enchantName = helper.readString(buffer);
        int enchantNetId = VarInts.readUnsignedInt(buffer);
        return new EnchantOptionData(cost, primarySlot, enchants0, enchants1, enchants2, enchantName, enchantNetId);
    }

    @Override
    protected void serializeEnchant(ByteBuf buffer, EnchantData enchant) {
        VarInts.writeUnsignedInt(buffer, enchant.type());
        buffer.writeByte(enchant.level());
    }

    @Override
    protected EnchantData deserializeEnchant(ByteBuf buffer) {
        int type = VarInts.readUnsignedInt(buffer);
        int level = buffer.readUnsignedByte();
        return new EnchantData(type, level);
    }
}
