package org.cloudburstmc.protocol.bedrock.codec.v2168;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.data.skin.ImageData;
import org.cloudburstmc.protocol.bedrock.data.skin.PersonaPieceData;
import org.cloudburstmc.protocol.bedrock.data.skin.PersonaPieceTintData;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BedrockCodecHelper_v2168SkinTest {

    @Test
    @SuppressWarnings("deprecation")
    void writesSkinsFromLoginDataTheWayBdsDoes() {
        // Login data carries "persona_" tint types and an "#RRGGBB" skin colour, as a 1.26.52 client sends them.
        SerializedSkin skin = SerializedSkin.builder()
                .skinId("persona-id")
                .playFabId("F17248EADD1FD0A8")
                .skinResourcePatch("{\"geometry\":{\"default\":\"geometry.persona\"}}")
                .skinData(ImageData.of(1, 1, new byte[4]))
                .capeData(ImageData.EMPTY)
                .geometryData("")
                .geometryDataEngineVersion("1.14.0")
                .animationData("")
                .capeId("")
                .fullSkinId("persona-id")
                .armSize("wide")
                .skinColor("#7e5337")
                .personaPieces(List.of(new PersonaPieceData(
                        "8f96d1f8-e9bb-40d2-acc8-eb79746c5d7c", "persona_skeleton",
                        "2099de18-429a-465a-a49b-fc4710a17bb3", true, "")))
                .tintColors(List.of(new PersonaPieceTintData("persona_mouth", List.of("#0", "#0", "#ff45220e", "#0"))))
                .persona(true)
                .profileHash("hash")
                .build();

        var helper = Bedrock_v2193.CODEC.createHelper();
        ByteBuf buffer = Unpooled.buffer();
        try {
            helper.writeSkin(buffer, skin);
            String bytes = new String(ByteBufUtil.getBytes(buffer), StandardCharsets.ISO_8859_1);

            assertTrue(bytes.contains("\u0005mouth"), "tint type is written by its canonical name");
            assertFalse(bytes.contains("persona_mouth"));

            SerializedSkin decoded = helper.readSkin(buffer);
            assertEquals("#ff7e5337", decoded.getSkinColor());
            assertEquals("mouth", decoded.getTintColors().getFirst().type());
            assertEquals("skeleton", decoded.getPersonaPieces().getFirst().type());
            assertEquals("F17248EADD1FD0A8", decoded.getPlayFabId());
            assertEquals("hash", decoded.getProfileHash());
            assertFalse(buffer.isReadable());
        } finally {
            buffer.release();
        }
    }

    @Test
    void keepsAnExplicitAlphaInTheSkinColour() {
        assertEquals(0x407e5337, SerializedSkin.builder().skinId("id").skinColor("#407e5337").build().getColor().getRGB());
        assertEquals(0xff7e5337, SerializedSkin.builder().skinId("id").skinColor("7e5337").build().getColor().getRGB());
        assertEquals(0, SerializedSkin.builder().skinId("id").build().getColor().getRGB());
    }
}
