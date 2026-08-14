package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.ScoreInfo;
import org.cloudburstmc.protocol.bedrock.packet.SetScorePacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

public class SetScoreSerializer_v2168_hotfix4 extends SetScoreSerializer_v2168 {

    public static final SetScoreSerializer_v2168_hotfix4 INSTANCE = new SetScoreSerializer_v2168_hotfix4();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SetScorePacket packet) {
        helper.writeArray(buffer, packet.getInfos(), (buf, scoreInfo) -> {
            VarInts.writeUnsignedInt(buffer, scoreInfo.getType().ordinal());
            helper.writeString(buf, TYPES[scoreInfo.getType().ordinal()]);

            VarInts.writeLong(buf, scoreInfo.getScoreboardId());

            switch (scoreInfo.getType()) {
                case INVALID:
                    helper.writeOptional(buf, objectiveId -> !objectiveId.isEmpty(), scoreInfo.getObjectiveId(), (optionalBuffer, objectiveId) -> {
                        buffer.writeBoolean(true);
                        helper.writeString(optionalBuffer, objectiveId);
                    });
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
            helper.readString(buf);

            long scoreboardId = VarInts.readLong(buf);

            String objectiveId = null;
            int score;
            switch (type) {
                case INVALID:
                    if (buffer.readBoolean()) {
                        objectiveId = helper.readOptional(buf, null, helper::readString);
                    }
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
