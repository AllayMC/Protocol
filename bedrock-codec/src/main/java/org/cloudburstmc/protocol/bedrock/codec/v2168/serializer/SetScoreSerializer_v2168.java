package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.SetScoreSerializer_v291;
import org.cloudburstmc.protocol.bedrock.data.ScoreInfo;
import org.cloudburstmc.protocol.bedrock.packet.SetScorePacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetScoreSerializer_v2168 extends SetScoreSerializer_v291 {

    public static final SetScoreSerializer_v2168 INSTANCE = new SetScoreSerializer_v2168();

    private static final String[] TYPES = {"remove", "changeplayer", "changeentity", "changefakeplayer"};
    private static final Logger log = LoggerFactory.getLogger(SetScoreSerializer_v2168.class);

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetScorePacket packet) {
        helper.writeArray(buffer, packet.getInfos(), (buf, scoreInfo) -> {
            VarInts.writeUnsignedInt(buffer, scoreInfo.getType().ordinal());
            helper.writeString(buf, TYPES[scoreInfo.getType().ordinal()]);

            VarInts.writeLong(buf, scoreInfo.getScoreboardId());

            switch (scoreInfo.getType()) {
                case INVALID:
                    helper.writeOptional(buf, value -> !value.isEmpty(), scoreInfo.getObjectiveId(), helper::writeString);
                    break;
                case ENTITY:
                case PLAYER:
                    if (scoreInfo.getObjectiveId().isEmpty() && log.isDebugEnabled()) {
                        log.debug("SetScorePacket with empty ObjectiveId");
                    }
                    helper.writeString(buf, scoreInfo.getObjectiveId().isEmpty() ? " " : scoreInfo.getObjectiveId());
                    buf.writeIntLE(scoreInfo.getScore());
                    VarInts.writeLong(buf, scoreInfo.getEntityId());
                    break;
                case FAKE:
                    if (scoreInfo.getObjectiveId().isEmpty() && log.isDebugEnabled()) {
                        log.debug("SetScorePacket with empty ObjectiveId");
                    }
                    if (scoreInfo.getName().isEmpty() && log.isDebugEnabled()) {
                        log.debug("SetScorePacket with empty Name");
                    }
                    helper.writeString(buf, scoreInfo.getObjectiveId().isEmpty() ? " " : scoreInfo.getObjectiveId());
                    buf.writeIntLE(scoreInfo.getScore());
                    helper.writeString(buf, scoreInfo.getName().isEmpty() ? " " : scoreInfo.getName());
                    break;
                default:
                    throw new IllegalStateException("ScoreInfo.ScorerType");
            }
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SetScorePacket packet) {
        helper.readArray(buffer, packet.getInfos(), buf -> {
            ScoreInfo.ScorerType type = ScoreInfo.ScorerType.values()[VarInts.readUnsignedInt(buffer)];
            helper.readString(buf); //type

            long scoreboardId = VarInts.readLong(buf);

            String objectiveId;
            int score;
            switch (type) {
                case INVALID:
                    objectiveId = helper.readOptional(buf, null, helper::readString);
                    return new ScoreInfo(scoreboardId, objectiveId == null ? "" : objectiveId, 0);
                case ENTITY:
                case PLAYER:
                    objectiveId = helper.readString(buf);
                    score = buf.readIntLE();
                    long entityId = VarInts.readLong(buf);
                    return new ScoreInfo(scoreboardId, objectiveId, score, type, entityId);
                case FAKE:
                    objectiveId = helper.readString(buf);
                    score = buf.readIntLE();
                    String name = helper.readString(buf);
                    return new ScoreInfo(scoreboardId, objectiveId, score, name);
                default:
                    throw new IllegalStateException("ScoreInfo.ScorerType");
            }
        });
    }
}
