package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v776.serializer.CreativeContentSerializer_v776;
import org.cloudburstmc.protocol.bedrock.data.inventory.CreativeItemCategory;
import org.cloudburstmc.protocol.bedrock.data.inventory.CreativeItemGroup;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.packet.CreativeContentPacket;

public class CreativeContentSerializer_v2168 extends CreativeContentSerializer_v776 {

    public static final CreativeContentSerializer_v2168 INSTANCE = new CreativeContentSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CreativeContentPacket packet) {
        helper.writeArray(buffer, packet.getGroups(), this::writeCreativeGroup);
        helper.writeArray(buffer, packet.getContents(), this::writeCreativeItem);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CreativeContentPacket packet) {
        helper.readArray(buffer, packet.getGroups(), this::readCreativeGroup);
        helper.readArray(buffer, packet.getContents(), this::readCreativeItem);
    }

    protected CreativeItemGroup readCreativeGroup(ByteBuf buffer, BedrockCodecHelper helper) {
        CreativeItemCategory category = CATEGORIES[buffer.readUnsignedByte()];
        String name = helper.readString(buffer);
        ItemData icon = helper.readItemInstance(buffer);
        return new CreativeItemGroup(category, name, icon);
    }

    protected void writeCreativeGroup(ByteBuf buffer, BedrockCodecHelper helper, CreativeItemGroup item) {
        buffer.writeByte(item.category().ordinal());
        helper.writeString(buffer, item.name());
        helper.writeItemInstance(buffer, item.icon());
    }
}
