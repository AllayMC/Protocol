package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.PartyChangedPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyChangedSerializer_v974 implements BedrockPacketSerializer<PartyChangedPacket> {
    public static final PartyChangedSerializer_v974 INSTANCE = new PartyChangedSerializer_v974();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PartyChangedPacket packet) {
        helper.writeOptionalNull(buffer, packet.getPartyId(), (buf, aHelper, partyId) -> {
            aHelper.writeString(buf, partyId);
            buf.writeBoolean(packet.isPartyLeader());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PartyChangedPacket packet) {
        PartyInfo info = helper.readOptional(buffer, null, (buf, aHelper) ->
                new PartyInfo(aHelper.readString(buf), buf.readBoolean()));
        if (info == null) {
            packet.setPartyId(null);
            packet.setPartyLeader(false);
            return;
        }

        packet.setPartyId(info.partyId());
        packet.setPartyLeader(info.partyLeader());
    }

    private record PartyInfo(String partyId, boolean partyLeader) {
    }
}
