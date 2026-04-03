package org.cloudburstmc.protocol.bedrock.codec.v776.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemVersion;
import org.cloudburstmc.protocol.bedrock.packet.ItemRegistryPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemRegistrySerializer_v776 implements BedrockPacketSerializer<ItemRegistryPacket> {
    public static final ItemRegistrySerializer_v776 INSTANCE = new ItemRegistrySerializer_v776();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ItemRegistryPacket packet) {
        helper.writeArray(buffer, packet.getItems(), (buf, packetHelper, item) -> {
            packetHelper.writeString(buf, item.identifier());
            buf.writeShortLE(item.runtimeId());
            buf.writeBoolean(item.componentBased());
            VarInts.writeInt(buf, item.version().ordinal());
            if (item.componentData() != null) { // We should check for isComponentBased() instead, but for some reason there can be non-data driven items with component data
                packetHelper.writeTag(buf, item.componentData());
            } else {
                packetHelper.writeTag(buf, NbtMap.EMPTY);
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ItemRegistryPacket packet) {
        helper.readArray(buffer, packet.getItems(), (buf, packetHelper) -> {
            String name = packetHelper.readString(buf);
            short itemId = buf.readShortLE();
            boolean componentBased = buf.readBoolean();
            int version = VarInts.readInt(buf);
            NbtMap data = packetHelper.readTag(buf, NbtMap.class);
            return new SimpleItemDefinition(name, itemId, ItemVersion.from(version), componentBased, data);
        });
    }
}
