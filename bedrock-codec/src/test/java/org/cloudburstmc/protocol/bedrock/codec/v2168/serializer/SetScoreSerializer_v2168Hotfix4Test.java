package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168_hotfix4;
import org.cloudburstmc.protocol.bedrock.data.ScoreInfo;
import org.cloudburstmc.protocol.bedrock.packet.SetScorePacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetScoreSerializer_v2168Hotfix4Test {

    private static final BedrockCodec CODEC = Bedrock_v2168_hotfix4.CODEC;
    private static final BedrockCodecHelper HELPER = CODEC.createHelper();

    @Test
    void hotfixCodecUsesTheUpdatedSetScoreSerializer() {
        assertEquals(2168, CODEC.getProtocolVersion());
        assertEquals("1.26.44", CODEC.getMinecraftVersion());
        assertSame(SetScoreSerializer_v2168_hotfix4.INSTANCE,
                CODEC.getPacketDefinition(SetScorePacket.class).serializer());
    }

    @Test
    void writesBothPresenceFlagsForAnInvalidScoreWithAnObjective() throws Exception {
        ByteBuf buffer = encodeInvalidScore("objective");
        try {
            assertEquals(1, VarInts.readUnsignedInt(buffer));
            assertEquals(ScoreInfo.ScorerType.INVALID.ordinal(), VarInts.readUnsignedInt(buffer));
            assertEquals("remove", HELPER.readString(buffer));
            assertEquals(42, VarInts.readLong(buffer));
            assertTrue(buffer.readBoolean());
            assertTrue(buffer.readBoolean());
            assertEquals("objective", HELPER.readString(buffer));
            assertFalse(buffer.isReadable());
        } finally {
            buffer.release();
        }
    }

    @Test
    void writesNoNestedOptionalForAnInvalidScoreWithoutAnObjective() throws Exception {
        ByteBuf buffer = encodeInvalidScore("");
        try {
            assertEquals(1, VarInts.readUnsignedInt(buffer));
            assertEquals(ScoreInfo.ScorerType.INVALID.ordinal(), VarInts.readUnsignedInt(buffer));
            assertEquals("remove", HELPER.readString(buffer));
            assertEquals(42, VarInts.readLong(buffer));
            assertFalse(buffer.readBoolean());
            assertFalse(buffer.isReadable());
        } finally {
            buffer.release();
        }
    }

    @Test
    void decodesTheHotfixInvalidScoreLayout() throws Exception {
        ByteBuf buffer = encodeInvalidScore("objective");
        try {
            int packetId = CODEC.getPacketDefinition(SetScorePacket.class).id();
            SetScorePacket decoded = (SetScorePacket) CODEC.tryDecode(HELPER, buffer, packetId);
            ScoreInfo score = decoded.getInfos().get(0);

            assertEquals(42, score.getScoreboardId());
            assertEquals("objective", score.getObjectiveId());
            assertEquals(ScoreInfo.ScorerType.INVALID, score.getType());
        } finally {
            buffer.release();
        }
    }

    private ByteBuf encodeInvalidScore(String objectiveId) throws Exception {
        SetScorePacket packet = new SetScorePacket();
        packet.getInfos().add(new ScoreInfo(42, objectiveId, 0));

        ByteBuf buffer = Unpooled.buffer();
        CODEC.tryEncode(HELPER, buffer, packet);
        return buffer;
    }
}
