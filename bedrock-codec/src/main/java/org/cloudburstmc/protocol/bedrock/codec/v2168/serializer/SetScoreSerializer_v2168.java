package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.SetScoreSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.ScoreInfo;
import org.cloudburstmc.protocol.bedrock.packet.SetScorePacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class SetScoreSerializer_v2168 extends SetScoreSerializer_v291 {

    public static final SetScoreSerializer_v2168 INSTANCE = new SetScoreSerializer_v2168();

    private static final String[] TYPES = {"remove", "changeplayer", "changeentity", "changefakeplayer"};

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetScorePacket packet) {
        helper.writeArray(buffer, packet.getInfos(), (buf, scoreInfo) -> {
            VarInts.writeUnsignedInt(buffer, scoreInfo.getType().ordinal());
            helper.writeString(buf, TYPES[scoreInfo.getType().ordinal()]);

            VarInts.writeLong(buf, scoreInfo.getScoreboardId());

            helper.writeString(buf, scoreInfo.getObjectiveId());

            switch (scoreInfo.getType()) {
                case ENTITY:
                case PLAYER:
                    buf.writeIntLE(scoreInfo.getScore());
                    VarInts.writeLong(buf, scoreInfo.getEntityId());
                    break;
                case FAKE:
                    buf.writeIntLE(scoreInfo.getScore());
                    helper.writeString(buf, scoreInfo.getName());
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetScorePacket packet) {
        helper.readArray(buffer, packet.getInfos(), buf -> {
            ScoreInfo.ScorerType type = ScoreInfo.ScorerType.values()[VarInts.readUnsignedInt(buffer)];
            helper.readString(buf); //type

            long scoreboardId = VarInts.readLong(buf);

            String objectiveId = helper.readString(buf);
            int score;
            switch (type) {
                case ENTITY:
                case PLAYER:
                    score = buf.readIntLE();
                    long entityId = VarInts.readLong(buf);
                    return new ScoreInfo(scoreboardId, objectiveId, score, type, entityId);
                case FAKE:
                    score = buf.readIntLE();
                    String name = helper.readString(buf);
                    return new ScoreInfo(scoreboardId, objectiveId, score, name);
                default:
                    return new ScoreInfo(scoreboardId, objectiveId, 0);
            }
        });
    }
}
