package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.DisconnectFailReason;
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisconnectSerializer_v974 implements BedrockPacketSerializer<DisconnectPacket> {
    public static final DisconnectSerializer_v974 INSTANCE = new DisconnectSerializer_v974();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, DisconnectPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getReason().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.isMessageSkipped() ? 1 : 0);
        if (!packet.isMessageSkipped()) {
            helper.writeString(buffer, packet.getKickMessage() == null ? "" : packet.getKickMessage());
            helper.writeString(buffer, packet.getFilteredMessage() == null ? "" : packet.getFilteredMessage());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, DisconnectPacket packet) {
        packet.setReason(DisconnectFailReason.values()[VarInts.readUnsignedInt(buffer)]);
        int type = VarInts.readUnsignedInt(buffer);
        packet.setMessageSkipped(type != 0);
        if (type == 0) {
            packet.setKickMessage(helper.readString(buffer));
            packet.setFilteredMessage(helper.readString(buffer));
        } else {
            packet.setKickMessage("");
            packet.setFilteredMessage("");
        }
    }
}
