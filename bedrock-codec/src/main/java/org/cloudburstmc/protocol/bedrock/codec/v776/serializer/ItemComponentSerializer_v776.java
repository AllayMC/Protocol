package org.cloudburstmc.protocol.bedrock.codec.v776.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition;
import org.cloudburstmc.protocol.bedrock.packet.ItemComponentPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemComponentSerializer_v776 implements BedrockPacketSerializer<ItemComponentPacket> {
    public static final ItemComponentSerializer_v776 INSTANCE = new ItemComponentSerializer_v776();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ItemComponentPacket packet) {
        helper.writeArray(buffer, packet.getItems(), (buf, packetHelper, item) -> {
            packetHelper.writeString(buf, item.getIdentifier());
            buf.writeShortLE(item.getRuntimeId());
            buf.writeBoolean(item.isComponentBased());
            VarInts.writeInt(buffer, item.getVersion());
            if (item.isComponentBased()) {
                packetHelper.writeTag(buf, item.getComponentData());
            } else {
                packetHelper.writeTag(buf, NbtMap.EMPTY);
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ItemComponentPacket packet) {
        helper.readArray(buffer, packet.getItems(), (buf, packetHelper) -> {
            String name = packetHelper.readString(buf);
            short itemId = buf.readShortLE();
            boolean componentBased = buf.readBoolean();
            int version = VarInts.readInt(buffer);
            NbtMap data = packetHelper.readTag(buf, NbtMap.class);
            return new SimpleItemDefinition(name, itemId, version, componentBased, data);
        });
    }
}
