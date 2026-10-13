package org.cloudburstmc.protocol.bedrock.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v2225.Bedrock_v2225;
import org.cloudburstmc.protocol.bedrock.data.FurnaceOptions;
import org.cloudburstmc.protocol.bedrock.data.PassengerOfBlockArguments;
import org.cloudburstmc.protocol.bedrock.data.attributelayer.*;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraPreset;
import org.cloudburstmc.protocol.bedrock.data.definitions.DimensionDefinition;
import org.cloudburstmc.protocol.bedrock.data.primitiveshape.PrimitiveText;
import org.cloudburstmc.protocol.bedrock.data.skin.*;
import org.cloudburstmc.protocol.bedrock.data.sound.*;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.util.VarInts;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UpstreamSyncSerializationTest {
    @Test
    void dimensionHeightIsEncodedAsMinimumAndRange() {
        for (BedrockCodec codec : List.of(Bedrock_v2193.CODEC, Bedrock_v2225.CODEC)) {
            boolean clouds = codec.getProtocolVersion() == 2225;
            DimensionDefinition definition = new DimensionDefinition("custom", 320, -64, 1, 2,
                    new UUID(0, 3), "plains", clouds ? 192 : 0, clouds);
            DimensionDataPacket packet = new DimensionDataPacket();
            packet.getDefinitions().add(definition);
            assertEquals(packet, roundTrip(codec, packet));
            ByteBuf buffer = Unpooled.buffer();
            try {
                codec.tryEncode(codec.createHelper(), buffer, packet);
                assertEquals(1, VarInts.readUnsignedInt(buffer));
                assertEquals("custom", codec.createHelper().readString(buffer));
                assertEquals(-64, VarInts.readInt(buffer));
                assertEquals(384, VarInts.readInt(buffer));
            } finally {
                buffer.release();
            }
        }
    }

    @Test
    void textShapesKeepImmutableFieldsAndLineGap() {
        for (BedrockCodec codec : List.of(Bedrock_v2168.CODEC, Bedrock_v2193.CODEC, Bedrock_v2225.CODEC)) {
            float gap = codec.getProtocolVersion() >= 2193 ? 1.75f : 0;
            PrimitiveText text = new PrimitiveText(4, 0, Vector3f.ZERO, 1f, Vector3f.ZERO, 2f,
                    Color.WHITE, "text", true, Color.BLUE, true, false, true, 64f, -55L, gap);
            PrimitiveShapesPacket packet = new PrimitiveShapesPacket();
            packet.getShapes().add(text);
            assertEquals(packet, roundTrip(codec, packet));
        }
    }

    @Test
    void cameraPresetsKeepForkFieldOrder() {
        CameraPreset preset = CameraPreset.builder().identifier("custom").parentPreset("minecraft:free")
                .pos(Vector3f.from(1, 2, 3)).pitch(12f).yaw(24f).viewOffset(Vector2f.from(2, 4))
                .minYawLimit(-60f).maxYawLimit(60f).applyInheritedStartingRotation(true)
                .startingRotation(Vector2f.from(10, 20)).build();
        CameraPresetsPacket packet = new CameraPresetsPacket();
        packet.getPresets().add(preset);
        assertEquals(packet, roundTrip(Bedrock_v2193.CODEC, packet));
    }

    @Test
    void entityDiagnosticsCarryPositionAndDimension() {
        ServerboundDiagnosticsPacket packet = new ServerboundDiagnosticsPacket();
        ServerboundDiagnosticsPacket.EntityDiagnostics entry = new ServerboundDiagnosticsPacket.EntityDiagnostics();
        entry.setDisplayName("actor");
        entry.setEntity("minecraft:pig");
        entry.setTimeInNs(123456L);
        entry.setPercentOfTotal(193);
        entry.setPosition(Vector3f.from(1, 2, 3));
        entry.setDimension("overworld");
        packet.getEntityDiagnostics().add(entry);
        assertEquals(packet, roundTrip(Bedrock_v2193.CODEC, packet));
    }

    @Test
    void furnaceOptionsAreRecordsAndWorkInBothDirections() {
        SetPlayerFurnaceOptionsPacket packet = new SetPlayerFurnaceOptionsPacket();
        packet.setType(SetPlayerFurnaceOptionsPacket.FurnaceType.values()[0]);
        packet.setOptions(new FurnaceOptions(FurnaceOptions.FurnaceLeftTabIndex.RECIPE_FOOD, true,
                FurnaceOptions.FurnaceLayout.DEFAULT));
        assertTrue(FurnaceOptions.class.isRecord());
        assertEquals(org.cloudburstmc.protocol.bedrock.data.PacketRecipient.BOTH,
                Bedrock_v2193.CODEC.getPacketDefinition(SetPlayerFurnaceOptionsPacket.class).recipient());
        assertEquals(packet, roundTrip(Bedrock_v2193.CODEC, packet));
    }

    @Test
    void playerListMovesPlayFabIdWithoutMutatingSkin() {
        SerializedSkin skin = SerializedSkin.builder().skinId("skin").playFabId("playfab")
                .geometryData("{}").skinResourcePatch("{\"geometry\":{\"default\":\"geometry.humanoid.custom\"}}")
                .skinData(ImageData.of(64, 64, new byte[64 * 64 * 4])).skinColor("#ff123456")
                .personaPieces(List.of(new PersonaPieceData("piece", "body", new UUID(0, 4).toString(), false, "product")))
                .tintColors(List.of(new PersonaPieceTintData("body", List.of("#ff000001", "#ff000002", "#ff000003", "#ff000004"))))
                .build();
        PlayerListPacket packet = new PlayerListPacket();
        PlayerListPacket.Entry entry = new PlayerListPacket.Entry(new UUID(0, 7));
        entry.setAction(PlayerListPacket.Action.ADD);
        entry.setName("player §w");
        entry.setEntityId(-50);
        entry.setXuid("123");
        entry.setPlatformChatId("");
        entry.setColor(Color.WHITE);
        entry.setSkin(skin);
        packet.getEntries().add(entry);
        PlayerListPacket decoded = (PlayerListPacket) roundTrip(Bedrock_v2225.CODEC, packet);
        SerializedSkin result = decoded.getEntries().getFirst().getSkin();
        assertEquals("playfab", result.getPlayFabId());
        assertEquals(skin.getPersonaPieces(), result.getPersonaPieces());
        assertEquals(skin.getTintColors(), result.getTintColors());
        assertEquals("player §w", decoded.getEntries().getFirst().getName());
        assertSame(skin, entry.getSkin());
        assertEquals("playfab", skin.getPlayFabId());
    }

    @Test
    void passengerRecordIsOptional() {
        SetPassengerOfBlockPacket packet = new SetPassengerOfBlockPacket();
        packet.setEntityUniqueId(-43);
        assertEquals(packet, roundTrip(Bedrock_v2225.CODEC, packet));
        packet.setData(new PassengerOfBlockArguments(Vector3i.from(1, -2, 3), Vector3f.from(.1f, .2f, .3f),
                90, 45, PassengerOfBlockArguments.EmoteType.RIDING));
        assertEquals(packet, roundTrip(Bedrock_v2225.CODEC, packet));
    }

    @Test
    void soundVariantsKeepRecordComponentOrder() {
        for (SoundData sound : List.of(new StopSoundData(), new PauseSoundData(), new ResumeSoundData(),
                new SetVolumeSoundData(.3f), new SetPitchSoundData(.7f), new FadeSoundData(.25f, 3f),
                new SeekToSoundData(12f))) {
            ClientboundUpdateSoundDataPacket packet = new ClientboundUpdateSoundDataPacket();
            packet.setServerSoundHandle(123);
            packet.setEvent(sound);
            assertEquals(sound, ((ClientboundUpdateSoundDataPacket) roundTrip(Bedrock_v2225.CODEC, packet)).getEvent());
        }
    }

    @Test
    void environmentAttributeVariantsKeepClockAndNoiseNames() {
        AttributeData from = new BoolAttributeData(false, BoolAttributeData.Operation.OVERRIDE);
        AttributeData to = new BoolAttributeData(true, BoolAttributeData.Operation.OVERRIDE);
        List<EnvironmentAttributeData> attributes = List.of(
                new EnvironmentAttributeData("constant", null, to, null, 0, 0, null),
                new EnvironmentAttributeData("transition", from, null, to, 12, 40, CameraEase.LINEAR,
                        "clock", 0, false, null, null),
                new EnvironmentAttributeData("noise", from, null, to, 14, 50, CameraEase.LINEAR,
                        "clock2", 7, true, "noise", new NoiseAlignment(NoiseAlignment.Type.MIN_LOCAL_TRANSITION_END, 6)));
        ClientboundAttributeLayerSyncPacket packet = new ClientboundAttributeLayerSyncPacket();
        packet.setData(new UpdateAttributeLayersData(List.of(new AttributeLayerData("layer", 0,
                new AttributeLayerSettings(3, new AttributeLayerSettings.FloatWeight(.5f), true, false), attributes))));
        assertEquals(packet, roundTrip(Bedrock_v2225.CODEC, packet));
    }

    @Test
    void nestedPacketDataUsesRecords() {
        ClientboundMatchmakingStatePacket packet = new ClientboundMatchmakingStatePacket();
        packet.setState(ClientboundMatchmakingStatePacket.MatchmakingState.MATCH_FOUND);
        packet.setDestinationName("server");
        packet.setOptions(new ClientboundMatchmakingStatePacket.MatchmakingStateOptions("player", true));
        assertTrue(packet.getOptions().getClass().isRecord());
        assertEquals(packet, roundTrip(Bedrock_v2225.CODEC, packet));
        ServerboundRegisterAudioContentPacket audio = new ServerboundRegisterAudioContentPacket();
        audio.getRegistrations().add(new ServerboundRegisterAudioContentPacket.AudioContentRegistrationEntry("id", "meta", "server", "play"));
        assertTrue(audio.getRegistrations().getFirst().getClass().isRecord());
        assertEquals(audio, roundTrip(Bedrock_v2225.CODEC, audio));
    }

    @Test
    void inputFlagsRejectInvalidCountsAndIndexes() {
        class InputReader extends org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.PlayerAuthInputSerializer_v2168 {
            void read(ByteBuf buffer) {
                readInputData(buffer, new PlayerAuthInputPacket());
            }
        }
        InputReader reader = new InputReader();
        ByteBuf buffer = Unpooled.buffer();
        try {
            VarInts.writeUnsignedInt(buffer, Integer.MAX_VALUE);
            assertThrows(IllegalArgumentException.class, () -> reader.read(buffer));
            buffer.clear();
            VarInts.writeUnsignedInt(buffer, 1);
            VarInts.writeInt(buffer, -1);
            assertThrows(IllegalArgumentException.class, () -> reader.read(buffer));
        } finally {
            buffer.release();
        }
    }

    @Test
    void v2168SoundFadeUsesDurationBeforeVolume() {
        ClientboundUpdateSoundDataPacket packet = new ClientboundUpdateSoundDataPacket();
        packet.setServerSoundHandle(4);
        packet.setFade(new FadeSoundData(.25f, 3f));
        ByteBuf buffer = Unpooled.buffer();
        try {
            Bedrock_v2168.CODEC.tryEncode(Bedrock_v2168.CODEC.createHelper(), buffer, packet);
            assertEquals(4, buffer.readLongLE());
            assertEquals(0, VarInts.readUnsignedInt(buffer));
            assertEquals(0, VarInts.readUnsignedInt(buffer));
            assertEquals(0, VarInts.readUnsignedInt(buffer));
            assertEquals(3, VarInts.readUnsignedInt(buffer));
            assertEquals(3f, buffer.readFloatLE());
            assertEquals(.25f, buffer.readFloatLE());
            assertEquals(packet.getFade(), ((ClientboundUpdateSoundDataPacket) roundTrip(Bedrock_v2168.CODEC, packet)).getFade());
        } finally {
            buffer.release();
        }
    }

    private static BedrockPacket roundTrip(BedrockCodec codec, BedrockPacket packet) {
        ByteBuf buffer = Unpooled.buffer();
        try {
            codec.tryEncode(codec.createHelper(), buffer, packet);
            var definition = codec.getPacketDefinition(packet.getClass());
            BedrockPacket decoded = codec.tryDecode(codec.createHelper(), buffer, definition.id(), definition.recipient());
            assertFalse(buffer.isReadable(), packet.getPacketType() + " trailing bytes");
            return decoded;
        } finally {
            buffer.release();
        }
    }
}
