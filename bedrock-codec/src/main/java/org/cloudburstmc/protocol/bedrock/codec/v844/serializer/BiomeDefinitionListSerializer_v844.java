package org.cloudburstmc.protocol.bedrock.codec.v844.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v827.serializer.BiomeDefinitionListSerializer_v827;
import org.cloudburstmc.protocol.bedrock.data.biome.*;
import org.cloudburstmc.protocol.common.util.Preconditions;
import org.cloudburstmc.protocol.common.util.SequencedHashSet;
import org.cloudburstmc.protocol.common.util.VarInts;
import org.cloudburstmc.protocol.common.util.index.IndexedList;

import java.awt.*;
import java.util.List;

public class BiomeDefinitionListSerializer_v844 extends BiomeDefinitionListSerializer_v827 {

    public static final BiomeDefinitionListSerializer_v844 INSTANCE = new BiomeDefinitionListSerializer_v844();

    @Override
    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionData definition, SequencedHashSet<String> strings) {
        this.writeDefinitionId(buffer, helper, definition);
        buffer.writeFloatLE(definition.temperature());
        buffer.writeFloatLE(definition.downfall());
        buffer.writeFloatLE(definition.foliageSnow());
        buffer.writeFloatLE(definition.depth());
        buffer.writeFloatLE(definition.scale());
        buffer.writeIntLE(definition.mapWaterColor().getRGB());
        buffer.writeBoolean(definition.rain());
        helper.writeOptionalNull(buffer, definition.tags(), (byteBuf, aHelper, tags) -> {
            List<String> values = tags.get();
            VarInts.writeUnsignedInt(byteBuf, values.size());
            for (String tag : values) {
                byteBuf.writeShortLE(strings.addAndGetIndex(tag));
            }
        });
        helper.writeOptionalNull(buffer, definition.chunkGenData(),
                (buf, aHelper, data) -> writeDefinitionChunkGen(buf, aHelper, data, strings));
    }

    @Override
    protected BiomeDefinitionData readDefinition(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        Integer id = this.readDefinitionId(buffer, helper);
        float temperature = buffer.readFloatLE();
        float downfall = buffer.readFloatLE();
        float foliageSnow = buffer.readFloatLE();
        float depth = buffer.readFloatLE();
        float scale = buffer.readFloatLE();
        Color mapWaterColor = new Color(buffer.readIntLE(), true);
        boolean rain = buffer.readBoolean();


        IndexedList<String> tags = helper.readOptional(buffer, null, byteBuf -> {
            int length = VarInts.readUnsignedInt(byteBuf);
            Preconditions.checkArgument(byteBuf.isReadable(length * 2), "Not enough readable bytes for tags");
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                array[i] = byteBuf.readUnsignedShortLE();
            }
            return new IndexedList<>(strings, array);
        });

        BiomeDefinitionChunkGenData chunkGenData = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readDefinitionChunkGen(buf, aHelper, strings));

        return new BiomeDefinitionData(id, temperature, downfall, 0, 0, 0, 0, foliageSnow, depth, scale,
                mapWaterColor, rain, tags, chunkGenData);
    }

    @Override
    protected void writeDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionChunkGenData definitionChunkGen,
                                           SequencedHashSet<String> strings) {
        helper.writeOptionalNull(buffer, definitionChunkGen.climate(), this::writeClimate);
        helper.writeOptionalNull(buffer, definitionChunkGen.consolidatedFeatures(),
                (buf, aHelper, consolidatedFeatures) -> this.writeConsolidatedFeatures(buf, aHelper, consolidatedFeatures, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.mountainParams(), this::writeMountainParamsData);
        helper.writeOptionalNull(buffer, definitionChunkGen.surfaceMaterialAdjustment(),
                (buf, aHelper, surfaceMaterialAdjustment) -> this.writeSurfaceMaterialAdjustment(buf, aHelper, surfaceMaterialAdjustment, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.surfaceMaterial(), this::writeSurfaceMaterial);
        buffer.writeBoolean(definitionChunkGen.hasDefaultOverworldSurface()); // new
        buffer.writeBoolean(definitionChunkGen.hasSwampSurface());
        buffer.writeBoolean(definitionChunkGen.hasFrozenOceanSurface());
        buffer.writeBoolean(definitionChunkGen.hasTheEndSurface());
        helper.writeOptionalNull(buffer, definitionChunkGen.mesaSurface(), this::writeMesaSurface);
        helper.writeOptionalNull(buffer, definitionChunkGen.cappedSurface(), this::writeCappedSurface);
        helper.writeOptionalNull(buffer, definitionChunkGen.overworldGenRules(),
                (buf, aHelper, overworldGenRules) -> this.writeOverworldGenRules(buf, aHelper, overworldGenRules, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.multinoiseGenRules(), this::writeMultinoiseGenRules);
        helper.writeOptionalNull(buffer, definitionChunkGen.legacyWorldGenRules(),
                (buf, aHelper, legacyWorldGenRules) -> this.writeLegacyWorldGenRules(buf, aHelper, legacyWorldGenRules, strings));
    }

    @Override
    protected BiomeDefinitionChunkGenData readDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiomeClimateData climate = helper.readOptional(buffer, null, this::readClimate);
        List<BiomeConsolidatedFeatureData> consolidatedFeatures = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readConsolidatedFeatures(buf, aHelper, strings));
        BiomeMountainParamsData mountainParams = helper.readOptional(buffer, null, this::readMountainParamsData);
        BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readSurfaceMaterialAdjustment(buf, aHelper, strings));
        BiomeSurfaceMaterialData surfaceMaterial = helper.readOptional(buffer, null, this::readSurfaceMaterial);
        boolean hasDefaultOverworldSurface = buffer.readBoolean(); // new
        boolean hasSwampSurface = buffer.readBoolean();
        boolean hasFrozenOceanSurface = buffer.readBoolean();
        boolean hasTheEndSurface = buffer.readBoolean();
        BiomeMesaSurfaceData mesaSurface = helper.readOptional(buffer, null, this::readMesaSurface);
        BiomeCappedSurfaceData cappedSurface = helper.readOptional(buffer, null, this::readCappedSurface);
        BiomeOverworldGenRulesData overworldGenRules = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readOverworldGenRules(buf, aHelper, strings));
        BiomeMultinoiseGenRulesData multinoiseGenRules = helper.readOptional(buffer, null, this::readMultinoiseGenRules);
        BiomeLegacyWorldGenRulesData legacyWorldGenRules = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readLegacyWorldGenRules(buf, aHelper, strings));

        return new BiomeDefinitionChunkGenData(climate, consolidatedFeatures,
                mountainParams, surfaceMaterialAdjustment,
                surfaceMaterial, hasDefaultOverworldSurface, hasSwampSurface,
                hasFrozenOceanSurface, hasTheEndSurface,
                mesaSurface, cappedSurface,
                overworldGenRules, multinoiseGenRules,
                legacyWorldGenRules, null, null);
    }

    @Override
    protected void writeClimate(ByteBuf buffer, BedrockCodecHelper helper, BiomeClimateData climate) {
        buffer.writeFloatLE(climate.temperature());
        buffer.writeFloatLE(climate.downfall());
        buffer.writeFloatLE(climate.snowAccumulationMin());
        buffer.writeFloatLE(climate.snowAccumulationMax());
    }

    @Override
    protected BiomeClimateData readClimate(ByteBuf buffer, BedrockCodecHelper helper) {
        float temperature = buffer.readFloatLE();
        float downfall = buffer.readFloatLE();
        float snowAccumulationMin = buffer.readFloatLE();
        float snowAccumulationMax = buffer.readFloatLE();

        return new BiomeClimateData(temperature, downfall, 0, 0, 0, 0, snowAccumulationMin, snowAccumulationMax);
    }
}
