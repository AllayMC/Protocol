package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v800.serializer.PlayerListSerializer_v800;
import org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class PlayerListSerializer_v2168 extends PlayerListSerializer_v800 {

    public static final PlayerListSerializer_v2168 INSTANCE = new PlayerListSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerListPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

        for (PlayerListPacket.Entry entry : packet.getEntries()) {
            if (entry.getAction() == null) {
                throw new NullPointerException("PlayerListPacket.Entry action missing");
            }

            VarInts.writeUnsignedInt(buffer, entry.getAction() == PlayerListPacket.Action.ADD ? 1 : 0);
            buffer.writeByte(entry.getAction().ordinal());

            if (entry.getAction() == PlayerListPacket.Action.ADD) {
                this.writeEntryBase(buffer, helper, entry);
            } else {
                helper.writeUuid(buffer, entry.getUuid());
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerListPacket packet) {
        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            PlayerListPacket.Action action = VarInts.readUnsignedInt(buffer) == 1 ? PlayerListPacket.Action.ADD : PlayerListPacket.Action.REMOVE;
            buffer.readUnsignedByte();

            PlayerListPacket.Entry entry;
            if (action == PlayerListPacket.Action.ADD) {
                entry = this.readEntryBase(buffer, helper);
            } else {
                entry = new PlayerListPacket.Entry(helper.readUuid(buffer));
            }
            entry.setAction(action);
            packet.getEntries().add(entry);
        }
    }
}
