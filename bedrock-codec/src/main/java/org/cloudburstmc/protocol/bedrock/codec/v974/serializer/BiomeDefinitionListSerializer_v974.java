package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v924.serializer.BiomeDefinitionListSerializer_v924;
import org.cloudburstmc.protocol.bedrock.data.biome.*;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BiomeDefinitionListSerializer_v974 extends BiomeDefinitionListSerializer_v924 {
    public static final BiomeDefinitionListSerializer_v974 INSTANCE = new BiomeDefinitionListSerializer_v974();

    @Override
    protected void writeDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionChunkGenData definitionChunkGen,
                                           org.cloudburstmc.protocol.bedrock.util.SequencedHashSet<String> strings) {
        helper.writeOptionalNull(buffer, definitionChunkGen.climate(), this::writeClimate);
        helper.writeOptionalNull(buffer, definitionChunkGen.consolidatedFeatures(),
                (buf, aHelper, consolidatedFeatures) -> this.writeConsolidatedFeatures(buf, aHelper, consolidatedFeatures, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.mountainParams(), this::writeMountainParamsData);
        helper.writeOptionalNull(buffer, definitionChunkGen.surfaceMaterialAdjustment(),
                (buf, aHelper, surfaceMaterialAdjustment) -> this.writeSurfaceMaterialAdjustment(buf, aHelper, surfaceMaterialAdjustment, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.overworldGenRules(),
                (buf, aHelper, overworldGenRules) -> this.writeOverworldGenRules(buf, aHelper, overworldGenRules, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.multinoiseGenRules(), this::writeMultinoiseGenRules);
        helper.writeOptionalNull(buffer, definitionChunkGen.legacyWorldGenRules(),
                (buf, aHelper, legacyWorldGenRules) -> this.writeLegacyWorldGenRules(buf, aHelper, legacyWorldGenRules, strings));

        List<BiomeReplacementData> replacementBiomes = definitionChunkGen.replacementBiomes();
        if (replacementBiomes == null && definitionChunkGen.biomeReplacementData() != null) {
            replacementBiomes = List.of(definitionChunkGen.biomeReplacementData());
        }
        helper.writeOptionalNull(buffer, replacementBiomes, this::writeBiomeReplacementsData);
        helper.writeOptionalNull(buffer, definitionChunkGen.villageType(), (b, n) -> b.writeByte(n.intValue()));

        BiomeSurfaceBuilderData surfaceBuilderData = definitionChunkGen.surfaceBuilderData();
        if (surfaceBuilderData == null) {
            surfaceBuilderData = new BiomeSurfaceBuilderData(
                    definitionChunkGen.surfaceMaterial(),
                    definitionChunkGen.hasDefaultOverworldSurface(),
                    definitionChunkGen.hasSwampSurface(),
                    definitionChunkGen.hasFrozenOceanSurface(),
                    definitionChunkGen.hasTheEndSurface(),
                    definitionChunkGen.mesaSurface(),
                    definitionChunkGen.cappedSurface(),
                    null
            );
        }
        helper.writeOptionalNull(buffer, surfaceBuilderData, this::writeBiomeSurfaceBuilderData);
        helper.writeOptionalNull(buffer, definitionChunkGen.subSurfaceBuilderData(), this::writeBiomeSurfaceBuilderData);
    }

    @Override
    protected BiomeDefinitionChunkGenData readDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiomeClimateData climate = helper.readOptional(buffer, null, this::readClimate);
        List<BiomeConsolidatedFeatureData> consolidatedFeatures = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readConsolidatedFeatures(buf, aHelper, strings));
        BiomeMountainParamsData mountainParams = helper.readOptional(buffer, null, this::readMountainParamsData);
        BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readSurfaceMaterialAdjustment(buf, aHelper, strings));
        BiomeOverworldGenRulesData overworldGenRules = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readOverworldGenRules(buf, aHelper, strings));
        BiomeMultinoiseGenRulesData multinoiseGenRules = helper.readOptional(buffer, null, this::readMultinoiseGenRules);
        BiomeLegacyWorldGenRulesData legacyWorldGenRules = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readLegacyWorldGenRules(buf, aHelper, strings));
        List<BiomeReplacementData> replacementBiomes = helper.readOptional(buffer, null, this::readBiomeReplacementsData);
        Number villageType = helper.readOptional(buffer, null, byteBuf -> (int) byteBuf.readUnsignedByte());
        BiomeSurfaceBuilderData surfaceBuilderData = helper.readOptional(buffer, null, this::readBiomeSurfaceBuilderData);
        BiomeSurfaceBuilderData subSurfaceBuilderData = helper.readOptional(buffer, null, this::readBiomeSurfaceBuilderData);

        BiomeReplacementData replacementData = replacementBiomes != null && replacementBiomes.size() == 1 ? replacementBiomes.get(0) : null;
        BiomeSurfaceMaterialData surfaceMaterial = surfaceBuilderData == null ? null : surfaceBuilderData.surfaceMaterial();
        boolean hasDefaultOverworldSurface = surfaceBuilderData != null && surfaceBuilderData.hasDefaultOverworldSurface();
        boolean hasSwampSurface = surfaceBuilderData != null && surfaceBuilderData.hasSwampSurface();
        boolean hasFrozenOceanSurface = surfaceBuilderData != null && surfaceBuilderData.hasFrozenOceanSurface();
        boolean hasTheEndSurface = surfaceBuilderData != null && surfaceBuilderData.hasTheEndSurface();
        BiomeMesaSurfaceData mesaSurface = surfaceBuilderData == null ? null : surfaceBuilderData.mesaSurface();
        BiomeCappedSurfaceData cappedSurface = surfaceBuilderData == null ? null : surfaceBuilderData.cappedSurface();

        return new BiomeDefinitionChunkGenData(climate, consolidatedFeatures,
                mountainParams, surfaceMaterialAdjustment,
                surfaceMaterial, hasDefaultOverworldSurface, hasSwampSurface,
                hasFrozenOceanSurface, hasTheEndSurface,
                mesaSurface, cappedSurface,
                overworldGenRules, multinoiseGenRules,
                legacyWorldGenRules, replacementData, villageType,
                replacementBiomes, surfaceBuilderData, subSurfaceBuilderData);
    }

    protected void writeBiomeReplacementsData(ByteBuf buffer, BedrockCodecHelper helper, List<BiomeReplacementData> data) {
        helper.writeArray(buffer, data, this::writeBiomeReplacementData);
    }

    protected List<BiomeReplacementData> readBiomeReplacementsData(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BiomeReplacementData> biomeReplacements = new ObjectArrayList<>();
        helper.readArray(buffer, biomeReplacements, this::readBiomeReplacementData);
        return biomeReplacements;
    }

    protected void writeBiomeSurfaceBuilderData(ByteBuf buffer, BedrockCodecHelper helper, BiomeSurfaceBuilderData data) {
        helper.writeOptionalNull(buffer, data.surfaceMaterial(), this::writeSurfaceMaterial);
        buffer.writeBoolean(data.hasDefaultOverworldSurface());
        buffer.writeBoolean(data.hasSwampSurface());
        buffer.writeBoolean(data.hasFrozenOceanSurface());
        buffer.writeBoolean(data.hasTheEndSurface());
        helper.writeOptionalNull(buffer, data.mesaSurface(), this::writeMesaSurface);
        helper.writeOptionalNull(buffer, data.cappedSurface(), this::writeCappedSurface);
        helper.writeOptionalNull(buffer, data.noiseGradientSurface(), this::writeBiomeNoiseGradientSurfaceData);
    }

    protected BiomeSurfaceBuilderData readBiomeSurfaceBuilderData(ByteBuf buffer, BedrockCodecHelper helper) {
        BiomeSurfaceMaterialData surfaceMaterial = helper.readOptional(buffer, null, this::readSurfaceMaterial);
        boolean hasDefaultOverworldSurface = buffer.readBoolean();
        boolean hasSwampSurface = buffer.readBoolean();
        boolean hasFrozenOceanSurface = buffer.readBoolean();
        boolean hasTheEndSurface = buffer.readBoolean();
        BiomeMesaSurfaceData mesaSurface = helper.readOptional(buffer, null, this::readMesaSurface);
        BiomeCappedSurfaceData cappedSurface = helper.readOptional(buffer, null, this::readCappedSurface);
        BiomeNoiseGradientSurfaceData noiseGradientSurface = helper.readOptional(buffer, null, this::readBiomeNoiseGradientSurfaceData);
        return new BiomeSurfaceBuilderData(surfaceMaterial, hasDefaultOverworldSurface, hasSwampSurface,
                hasFrozenOceanSurface, hasTheEndSurface, mesaSurface, cappedSurface, noiseGradientSurface);
    }

    protected void writeBiomeNoiseGradientSurfaceData(ByteBuf buffer, BedrockCodecHelper helper, BiomeNoiseGradientSurfaceData data) {
        helper.writeArray(buffer, data.nonReplaceableBlocks(), this::writeBlock);
        helper.writeArray(buffer, data.gradientBlocks(), this::writeBlock);
        helper.writeString(buffer, data.noiseSeedString());
        VarInts.writeInt(buffer, data.firstOctave());
        helper.writeArray(buffer, data.amplitudes(), ByteBuf::writeFloatLE);
    }

    protected BiomeNoiseGradientSurfaceData readBiomeNoiseGradientSurfaceData(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BlockDefinition> nonReplaceableBlocks = new ObjectArrayList<>();
        List<BlockDefinition> gradientBlocks = new ObjectArrayList<>();
        helper.readArray(buffer, nonReplaceableBlocks, this::readBlock);
        helper.readArray(buffer, gradientBlocks, this::readBlock);
        String noiseSeedString = helper.readString(buffer);
        int firstOctave = VarInts.readInt(buffer);
        List<Float> amplitudes = new ObjectArrayList<>();
        helper.readArray(buffer, amplitudes, ByteBuf::readFloatLE);
        return new BiomeNoiseGradientSurfaceData(nonReplaceableBlocks, gradientBlocks, noiseSeedString, firstOctave, amplitudes);
    }
}
