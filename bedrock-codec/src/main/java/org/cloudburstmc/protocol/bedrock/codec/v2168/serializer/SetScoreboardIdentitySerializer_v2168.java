package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.SetScoreboardIdentitySerializer_v291;
import org.cloudburstmc.protocol.bedrock.packet.SetScoreboardIdentityPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import static org.cloudburstmc.protocol.bedrock.packet.SetScoreboardIdentityPacket.Action;
import static org.cloudburstmc.protocol.bedrock.packet.SetScoreboardIdentityPacket.Entry;

public class SetScoreboardIdentitySerializer_v2168 extends SetScoreboardIdentitySerializer_v291 {

    public static final SetScoreboardIdentitySerializer_v2168 INSTANCE = new SetScoreboardIdentitySerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetScoreboardIdentityPacket packet) {
        Action action = packet.getAction();
        buffer.writeByte(action.ordinal());
        helper.writeArray(buffer, packet.getEntries(), (buf, entry) -> {
            VarInts.writeLong(buffer, entry.scoreboardId());
            helper.writeOptional(buffer, value -> action == Action.ADD, entry.playerId(), VarInts::writeLong);
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetScoreboardIdentityPacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        helper.readArray(buffer, packet.getEntries(), buf -> {
            long scoreboardId = VarInts.readLong(buffer);
            long playerId = 0;
            if (buffer.readBoolean()) {
                playerId = VarInts.readLong(buffer);
            }
            return new Entry(scoreboardId, playerId);
        });
    }
}
