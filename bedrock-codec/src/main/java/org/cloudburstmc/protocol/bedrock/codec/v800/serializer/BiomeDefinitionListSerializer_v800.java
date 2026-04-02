package org.cloudburstmc.protocol.bedrock.codec.v800.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntObjectPair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.CoordinateEvaluationOrder;
import org.cloudburstmc.protocol.bedrock.data.ExpressionOp;
import org.cloudburstmc.protocol.bedrock.data.RandomDistributionType;
import org.cloudburstmc.protocol.bedrock.data.biome.*;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.packet.BiomeDefinitionListPacket;
import org.cloudburstmc.protocol.bedrock.util.*;
import org.cloudburstmc.protocol.bedrock.util.index.Indexed;
import org.cloudburstmc.protocol.bedrock.util.index.IndexedList;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@SuppressWarnings("deprecation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BiomeDefinitionListSerializer_v800 implements BedrockPacketSerializer<BiomeDefinitionListPacket> {

    public static final BiomeDefinitionListSerializer_v800 INSTANCE = new BiomeDefinitionListSerializer_v800();

    private static final BiomeTemperatureCategory[] TEMPERATURE_CATEGORIES = BiomeTemperatureCategory.values();
    private static final ExpressionOp[] EXPRESSION_OPS = ExpressionOp.values();
    private static final CoordinateEvaluationOrder[] EVALUATION_ORDERS = CoordinateEvaluationOrder.values();
    private static final RandomDistributionType[] RANDOM_DISTRIBUTION_TYPES = RandomDistributionType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionListPacket packet) {
        SequencedHashSet<String> strings = new SequencedHashSet<>();
        BiomeDefinitions biomeDefinitions = packet.getBiomes();
        Map<String, BiomeDefinitionData> biomes = biomeDefinitions.definitions();
        helper.writeArray(buffer, biomes.entrySet(), (byteBuf, aHelper, entry) -> {
            String name = entry.getKey();
            BiomeDefinitionData definition = entry.getValue();
            byteBuf.writeShortLE(strings.addAndGetIndex(name));
            writeDefinition(byteBuf, aHelper, definition, strings);
        });
        helper.writeArray(buffer, strings, (byteBuf, bedrockCodecHelper, biomeName) -> bedrockCodecHelper.writeString(byteBuf, biomeName));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionListPacket packet) {
        List<String> strings = new ObjectArrayList<>();
        List<IntObjectPair<BiomeDefinitionData>> biomeDefinitions = new ObjectArrayList<>();
        helper.readArray(buffer, biomeDefinitions, (byteBuf, bedrockCodecHelper) -> {
            int index = byteBuf.readUnsignedShortLE();
            return IntObjectPair.of(index, readDefinition(byteBuf, bedrockCodecHelper, strings));
        });
        IndexedBiomes indexedBiomes = new IndexedBiomes(biomeDefinitions, strings);

        helper.readArray(buffer, strings,
                (byteBuf, bedrockCodecHelper) -> bedrockCodecHelper.readString(byteBuf));
        packet.setBiomes(new BiomeDefinitions(indexedBiomes.get()));
    }

    protected void writeDefinitionId(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionData definition) {
        helper.writeOptional(buffer, Objects::nonNull, definition.id(), ByteBuf::writeShortLE);
    }

    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionData definition, SequencedHashSet<String> strings) {
        this.writeDefinitionId(buffer, helper, definition);
        buffer.writeFloatLE(definition.temperature());
        buffer.writeFloatLE(definition.downfall());
        buffer.writeFloatLE(definition.redSporeDensity());
        buffer.writeFloatLE(definition.blueSporeDensity());
        buffer.writeFloatLE(definition.ashDensity());
        buffer.writeFloatLE(definition.whiteAshDensity());
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

    protected Integer readDefinitionId(ByteBuf buffer, BedrockCodecHelper helper) {
        return helper.readOptional(buffer, null, (buf, aHelper) -> buf.readUnsignedShortLE());
    }

    protected BiomeDefinitionData readDefinition(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        Integer id = this.readDefinitionId(buffer, helper);
        float temperature = buffer.readFloatLE();
        float downfall = buffer.readFloatLE();
        float redSporeDensity = buffer.readFloatLE();
        float blueSporeDensity = buffer.readFloatLE();
        float ashDensity = buffer.readFloatLE();
        float whiteAshDensity = buffer.readFloatLE();
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

        return new BiomeDefinitionData(id, temperature, downfall, redSporeDensity, blueSporeDensity,
                ashDensity, whiteAshDensity, 0, depth, scale, mapWaterColor, rain, tags, chunkGenData);
    }

    protected void writeDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionChunkGenData definitionChunkGen,
                                           SequencedHashSet<String> strings) {
        helper.writeOptionalNull(buffer, definitionChunkGen.climate(), this::writeClimate);
        helper.writeOptionalNull(buffer, definitionChunkGen.consolidatedFeatures(),
                (buf, aHelper, consolidatedFeatures) -> this.writeConsolidatedFeatures(buf, aHelper, consolidatedFeatures, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.mountainParams(), this::writeMountainParamsData);
        helper.writeOptionalNull(buffer, definitionChunkGen.surfaceMaterialAdjustment(),
                (buf, aHelper, surfaceMaterialAdjustment) -> this.writeSurfaceMaterialAdjustment(buf, aHelper, surfaceMaterialAdjustment, strings));
        helper.writeOptionalNull(buffer, definitionChunkGen.surfaceMaterial(), this::writeSurfaceMaterial);
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

    protected BiomeDefinitionChunkGenData readDefinitionChunkGen(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiomeClimateData climate = helper.readOptional(buffer, null, this::readClimate);
        List<BiomeConsolidatedFeatureData> consolidatedFeatures = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readConsolidatedFeatures(buf, aHelper, strings));
        BiomeMountainParamsData mountainParams = helper.readOptional(buffer, null, this::readMountainParamsData);
        BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment = helper.readOptional(buffer, null,
                (buf, aHelper) -> this.readSurfaceMaterialAdjustment(buf, aHelper, strings));
        BiomeSurfaceMaterialData surfaceMaterial = helper.readOptional(buffer, null, this::readSurfaceMaterial);
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
                surfaceMaterial, false, hasSwampSurface,
                hasFrozenOceanSurface, hasTheEndSurface,
                mesaSurface, cappedSurface,
                overworldGenRules, multinoiseGenRules,
                legacyWorldGenRules, null, null);
    }

    protected void writeClimate(ByteBuf buffer, BedrockCodecHelper helper, BiomeClimateData climate) {
        buffer.writeFloatLE(climate.temperature());
        buffer.writeFloatLE(climate.downfall());
        buffer.writeFloatLE(climate.redSporeDensity());
        buffer.writeFloatLE(climate.blueSporeDensity());
        buffer.writeFloatLE(climate.ashDensity());
        buffer.writeFloatLE(climate.whiteAshDensity());
        buffer.writeFloatLE(climate.snowAccumulationMin());
        buffer.writeFloatLE(climate.snowAccumulationMax());
    }

    protected BiomeClimateData readClimate(ByteBuf buffer, BedrockCodecHelper helper) {
        float temperature = buffer.readFloatLE();
        float downfall = buffer.readFloatLE();
        float redSporeDensity = buffer.readFloatLE();
        float blueSporeDensity = buffer.readFloatLE();
        float ashDensity = buffer.readFloatLE();
        float whiteAshDensity = buffer.readFloatLE();
        float snowAccumulationMin = buffer.readFloatLE();
        float snowAccumulationMax = buffer.readFloatLE();

        return new BiomeClimateData(temperature, downfall, redSporeDensity, blueSporeDensity,
                ashDensity, whiteAshDensity, snowAccumulationMin, snowAccumulationMax);
    }

    protected void writeConsolidatedFeatures(ByteBuf buffer, BedrockCodecHelper helper, List<BiomeConsolidatedFeatureData> consolidatedFeatures,
                                             SequencedHashSet<String> strings) {
        helper.writeArray(buffer, consolidatedFeatures,
                (buf, aHelper, consolidatedFeature) -> this.writeConsolidatedFeature(buf, aHelper, consolidatedFeature, strings));
    }

    protected List<BiomeConsolidatedFeatureData> readConsolidatedFeatures(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        List<BiomeConsolidatedFeatureData> consolidatedFeatures = new ObjectArrayList<>();
        helper.readArray(buffer, consolidatedFeatures,
                (buf, aHelper) -> this.readConsolidatedFeature(buf, aHelper, strings));
        return consolidatedFeatures;
    }

    protected void writeConsolidatedFeature(ByteBuf buffer, BedrockCodecHelper helper,
                                            BiomeConsolidatedFeatureData consolidatedFeature,
                                            SequencedHashSet<String> strings) {
        this.writeScatterParam(buffer, helper, consolidatedFeature.scatter(), strings);
        buffer.writeShortLE(strings.addAndGetIndex(consolidatedFeature.feature()));
        buffer.writeShortLE(strings.addAndGetIndex(consolidatedFeature.identifier()));
        buffer.writeShortLE(strings.addAndGetIndex(consolidatedFeature.pass()));
        buffer.writeBoolean(consolidatedFeature.internalUse());
    }

    protected BiomeConsolidatedFeatureData readConsolidatedFeature(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiomeScatterParamData scatter = readScatterParam(buffer, helper, strings);
        Indexed<String> feature = new Indexed<>(strings, buffer.readShortLE());
        Indexed<String> identifier = new Indexed<>(strings, buffer.readShortLE());
        Indexed<String> pass = new Indexed<>(strings, buffer.readShortLE());
        boolean internalUse = buffer.readBoolean();

        return new BiomeConsolidatedFeatureData(scatter, feature.get(), identifier.get(), pass.get(), internalUse);
    }

    protected void writeScatterParam(ByteBuf buffer, BedrockCodecHelper helper, BiomeScatterParamData scatterParam,
                                     SequencedHashSet<String> strings) {
        helper.writeArray(buffer, scatterParam.coordinates(),
                (buf, aHelper, coordinate) -> this.writeCoordinate(buf, aHelper, coordinate, strings));
        VarInts.writeInt(buffer, scatterParam.evalOrder().ordinal());
        VarInts.writeInt(buffer, scatterParam.chancePercentType() == null ? -1 : scatterParam.chancePercentType().ordinal());
        buffer.writeShortLE(strings.addAndGetIndex(scatterParam.chancePercent()));
        buffer.writeIntLE(scatterParam.chanceNumerator());
        buffer.writeIntLE(scatterParam.changeDenominator());
        VarInts.writeInt(buffer, scatterParam.iterationsType() == null ? -1 : scatterParam.iterationsType().ordinal());
        buffer.writeShortLE(strings.addAndGetIndex(scatterParam.iterations()));
    }

    protected BiomeScatterParamData readScatterParam(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        List<BiomeCoordinateData> coordinates = new ObjectArrayList<>();
        helper.readArray(buffer, coordinates,
                (buf, aHelper) -> this.readCoordinate(buf, aHelper, strings));
        CoordinateEvaluationOrder evalOrder = EVALUATION_ORDERS[VarInts.readInt(buffer)];
        int chancePercentTypeInt = VarInts.readInt(buffer);
        ExpressionOp chancePercentType = chancePercentTypeInt == -1 ? null : EXPRESSION_OPS[chancePercentTypeInt];
        Indexed<String> chancePercent = new Indexed<>(strings, buffer.readShortLE());
        int chanceNumerator = buffer.readIntLE();
        int chanceDenominator = buffer.readIntLE();
        int iterationTypeInt = VarInts.readInt(buffer);
        ExpressionOp iterationsType = iterationTypeInt == -1 ? null : EXPRESSION_OPS[iterationTypeInt];
        Indexed<String> iterations = new Indexed<>(strings, buffer.readShortLE());

        return new BiomeScatterParamData(coordinates, evalOrder, chancePercentType,
                chancePercent.get(), chanceNumerator, chanceDenominator,
                iterationsType, iterations.get());
    }

    protected void writeCoordinate(ByteBuf buffer, BedrockCodecHelper helper, BiomeCoordinateData coordinate,
                                   SequencedHashSet<String> strings) {
        this.writeExpressionOp(buffer, coordinate.minValueType());
        buffer.writeShortLE(strings.addAndGetIndex(coordinate.minValue()));
        this.writeExpressionOp(buffer, coordinate.maxValueType());
        buffer.writeShortLE(strings.addAndGetIndex(coordinate.maxValue()));
        buffer.writeIntLE((int) coordinate.gridOffset());
        buffer.writeIntLE((int) coordinate.gridStepSize());
        VarInts.writeInt(buffer, coordinate.distribution().ordinal());
    }

    protected BiomeCoordinateData readCoordinate(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        ExpressionOp minValueType = this.readExpressionOp(buffer);
        Indexed<String> minValue = new Indexed<>(strings, buffer.readShortLE());
        ExpressionOp maxValueType = this.readExpressionOp(buffer);
        Indexed<String> maxValue = new Indexed<>(strings, buffer.readShortLE());
        long gridOffset = buffer.readUnsignedIntLE();
        long gridStepSize = buffer.readUnsignedIntLE();
        RandomDistributionType distribution = RANDOM_DISTRIBUTION_TYPES[VarInts.readInt(buffer)];

        return new BiomeCoordinateData(minValueType, minValue.get(), maxValueType,
                maxValue.get(), gridOffset, gridStepSize, distribution);
    }

    protected void writeMountainParamsData(ByteBuf buffer, BedrockCodecHelper helper, BiomeMountainParamsData mountainParams) {
        this.writeBlock(buffer, helper, mountainParams.steepBlock());
        buffer.writeBoolean(mountainParams.northSlopes());
        buffer.writeBoolean(mountainParams.southSlopes());
        buffer.writeBoolean(mountainParams.westSlopes());
        buffer.writeBoolean(mountainParams.eastSlopes());
        buffer.writeBoolean(mountainParams.topSlideEnabled());
    }

    protected BiomeMountainParamsData readMountainParamsData(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockDefinition steepBlock = this.readBlock(buffer, helper);
        boolean northSlopes = buffer.readBoolean();
        boolean southSlopes = buffer.readBoolean();
        boolean westSlopes = buffer.readBoolean();
        boolean eastSlopes = buffer.readBoolean();
        boolean topSlideEnabled = buffer.readBoolean();

        return new BiomeMountainParamsData(steepBlock, northSlopes, southSlopes,
                westSlopes, eastSlopes, topSlideEnabled);
    }

    protected void writeSurfaceMaterialAdjustment(ByteBuf buffer, BedrockCodecHelper helper,
                                                  BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment,
                                                  SequencedHashSet<String> strings) {
        helper.writeArray(buffer, surfaceMaterialAdjustment.biomeElements(),
                (buf, aHelper, biomeElement) -> this.writeBiomeElement(buf, aHelper, biomeElement, strings));
    }

    protected BiomeSurfaceMaterialAdjustmentData readSurfaceMaterialAdjustment(ByteBuf buffer, BedrockCodecHelper helper,
                                                                                List<String> strings) {
        List<BiomeElementData> biomeElements = new ObjectArrayList<>();
        helper.readArray(buffer, biomeElements,
                (buf, aHelper) -> this.readBiomeElement(buf, aHelper, strings));
        return new BiomeSurfaceMaterialAdjustmentData(biomeElements);
    }

    protected void writeBiomeElement(ByteBuf buffer, BedrockCodecHelper helper, BiomeElementData biomeElement,
                                     SequencedHashSet<String> strings) {
        buffer.writeFloatLE(biomeElement.noiseFrequencyScale());
        buffer.writeFloatLE(biomeElement.noiseLowerBound());
        buffer.writeFloatLE(biomeElement.noiseUpperBound());
        this.writeExpressionOp(buffer, biomeElement.heightMinType());
        buffer.writeShortLE(strings.addAndGetIndex(biomeElement.heightMin()));
        this.writeExpressionOp(buffer, biomeElement.heightMaxType());
        buffer.writeShortLE(strings.addAndGetIndex(biomeElement.heightMax()));
        this.writeSurfaceMaterial(buffer, helper, biomeElement.adjustedMaterials());
    }

    protected BiomeElementData readBiomeElement(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        float noiseFrequencyScale = buffer.readFloatLE();
        float noiseLowerBound = buffer.readFloatLE();
        float noiseUpperBound = buffer.readFloatLE();
        ExpressionOp heightMinType = this.readExpressionOp(buffer);
        Indexed<String> heightMin = new Indexed<>(strings, buffer.readShortLE());
        ExpressionOp heightMaxType = this.readExpressionOp(buffer);
        Indexed<String> heightMax = new Indexed<>(strings, buffer.readShortLE());
        BiomeSurfaceMaterialData adjustedMaterials = readSurfaceMaterial(buffer, helper);

        return new BiomeElementData(noiseFrequencyScale, noiseLowerBound, noiseUpperBound,
                heightMinType, heightMin.get(), heightMaxType, heightMax.get(), adjustedMaterials);
    }

    protected void writeSurfaceMaterial(ByteBuf buffer, BedrockCodecHelper helper, BiomeSurfaceMaterialData surfaceMaterial) {
        this.writeBlock(buffer, helper, surfaceMaterial.topBlock());
        this.writeBlock(buffer, helper, surfaceMaterial.midBlock());
        this.writeBlock(buffer, helper, surfaceMaterial.seaFloorBlock());
        this.writeBlock(buffer, helper, surfaceMaterial.foundationBlock());
        this.writeBlock(buffer, helper, surfaceMaterial.seaBlock());
        buffer.writeIntLE(surfaceMaterial.seaFloorDepth());
    }

    protected BiomeSurfaceMaterialData readSurfaceMaterial(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockDefinition topBlock = this.readBlock(buffer, helper);
        BlockDefinition midBlock = this.readBlock(buffer, helper);
        BlockDefinition seaFloorBlock = this.readBlock(buffer, helper);
        BlockDefinition foundationBlock = this.readBlock(buffer, helper);
        BlockDefinition seaBlock = this.readBlock(buffer, helper);
        int seaFloorDepth = buffer.readIntLE();

        return new BiomeSurfaceMaterialData(topBlock, midBlock, seaFloorBlock, foundationBlock, seaBlock, seaFloorDepth);
    }


    protected void writeMesaSurface(ByteBuf buffer, BedrockCodecHelper helper, BiomeMesaSurfaceData mesaSurface) {
        this.writeBlock(buffer, helper, mesaSurface.clayMaterial());
        this.writeBlock(buffer, helper, mesaSurface.hardClayMaterial());
        buffer.writeBoolean(mesaSurface.brycePillars());
        buffer.writeBoolean(mesaSurface.hasForest());
    }

    protected BiomeMesaSurfaceData readMesaSurface(ByteBuf buffer, BedrockCodecHelper helper) {
        BlockDefinition clayMaterial = this.readBlock(buffer, helper);
        BlockDefinition hardClayMaterial = this.readBlock(buffer, helper);
        boolean brycePillars = buffer.readBoolean();
        boolean hasForest = buffer.readBoolean();

        return new BiomeMesaSurfaceData(clayMaterial, hardClayMaterial, brycePillars, hasForest);
    }


    protected void writeCappedSurface(ByteBuf buffer, BedrockCodecHelper helper, BiomeCappedSurfaceData cappedSurface) {
        helper.writeArray(buffer, cappedSurface.floorBlocks(), this::writeBlock);
        helper.writeArray(buffer, cappedSurface.ceilingBlocks(), this::writeBlock);
        helper.writeOptionalNull(buffer, cappedSurface.seaBlock(), this::writeBlock);
        helper.writeOptionalNull(buffer, cappedSurface.foundationBlock(), this::writeBlock);
        helper.writeOptionalNull(buffer, cappedSurface.beachBlock(), this::writeBlock);
    }

    protected BiomeCappedSurfaceData readCappedSurface(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BlockDefinition> floorBlocks = new ObjectArrayList<>();
        helper.readArray(buffer, floorBlocks, this::readBlock);
        List<BlockDefinition> ceilingBlocks = new ObjectArrayList<>();
        helper.readArray(buffer, ceilingBlocks, this::readBlock);
        BlockDefinition seaBlock = helper.readOptional(buffer, null, this::readBlock);
        BlockDefinition foundationBlock = helper.readOptional(buffer, null, this::readBlock);
        BlockDefinition beachBlock = helper.readOptional(buffer, null, this::readBlock);

        return new BiomeCappedSurfaceData(floorBlocks, ceilingBlocks, seaBlock, foundationBlock, beachBlock);
    }

    protected void writeOverworldGenRules(ByteBuf buffer, BedrockCodecHelper helper,
                                          BiomeOverworldGenRulesData overworldGenRules, SequencedHashSet<String> strings) {
        BiConsumer<ByteBuf, BiomeWeightedData> writeWeight =
                (buf, data) -> this.writeWeight(buf, data, strings);
        helper.writeArray(buffer, overworldGenRules.hillsTransformations(), writeWeight);
        helper.writeArray(buffer, overworldGenRules.mutateTransformations(), writeWeight);
        helper.writeArray(buffer, overworldGenRules.riverTransformations(), writeWeight);
        helper.writeArray(buffer, overworldGenRules.shoreTransformations(), writeWeight);
        TriConsumer<ByteBuf, BedrockCodecHelper, BiomeConditionalTransformationData> writeConditionalTransformation =
                (buf, aHelper, data) -> this.writeConditionalTransformation(buf, aHelper, data, strings);
        helper.writeArray(buffer, overworldGenRules.preHillsEdgeTransformations(), writeConditionalTransformation);
        helper.writeArray(buffer, overworldGenRules.postShoreTransformations(), writeConditionalTransformation);
        helper.writeArray(buffer, overworldGenRules.climateTransformations(), this::writeWeightedTemperature);
    }

    protected BiomeOverworldGenRulesData readOverworldGenRules(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        BiFunction<ByteBuf, BedrockCodecHelper, BiomeWeightedData> readWeight =
                (buf, aHelper) -> this.readWeight(buf, aHelper, strings);
        List<BiomeWeightedData> hillsTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, hillsTransformations, readWeight);
        List<BiomeWeightedData> mutateTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, mutateTransformations, readWeight);
        List<BiomeWeightedData> riverTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, riverTransformations, readWeight);
        List<BiomeWeightedData> shoreTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, shoreTransformations, readWeight);
        BiFunction<ByteBuf, BedrockCodecHelper, BiomeConditionalTransformationData> readConditionalTransformation =
                (buf, aHelper) -> this.readConditionalTransformation(buf, aHelper, strings);
        List<BiomeConditionalTransformationData> preHillsEdgeTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, preHillsEdgeTransformations, readConditionalTransformation);
        List<BiomeConditionalTransformationData> postShoreTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, postShoreTransformations, readConditionalTransformation);
        List<BiomeWeightedTemperatureData> climateTransformations = new ObjectArrayList<>();
        helper.readArray(buffer, climateTransformations, this::readWeightedTemperature);

        return new BiomeOverworldGenRulesData(hillsTransformations,
                mutateTransformations,
                riverTransformations,
                shoreTransformations,
                preHillsEdgeTransformations,
                postShoreTransformations,
                climateTransformations);
    }

    protected void writeWeight(ByteBuf buffer, BiomeWeightedData weightedData, SequencedHashSet<String> strings) {
        buffer.writeShortLE(strings.addAndGetIndex(weightedData.biome()));
        buffer.writeIntLE(weightedData.weight());
    }

    protected BiomeWeightedData readWeight(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        Indexed<String> biome = new Indexed<>(strings, buffer.readShortLE());
        int weight = buffer.readIntLE();
        return new BiomeWeightedData(biome.get(), weight);
    }

    protected void writeConditionalTransformation(ByteBuf buffer, BedrockCodecHelper helper,
                                                  BiomeConditionalTransformationData conditionalTransformation,
                                                  SequencedHashSet<String> strings) {
        helper.writeArray(buffer, conditionalTransformation.weightedBiomes(),
                (buf, data) -> writeWeight(buf, data, strings));
        buffer.writeShortLE(strings.addAndGetIndex(conditionalTransformation.conditionJson()));
        buffer.writeIntLE((int) conditionalTransformation.minPassingNeighbors());
    }

    protected BiomeConditionalTransformationData readConditionalTransformation(ByteBuf buffer, BedrockCodecHelper helper,
                                                                               List<String> strings) {
        List<BiomeWeightedData> weightedBiomes = new ObjectArrayList<>();
        helper.readArray(buffer, weightedBiomes, (buf, aHelper) -> readWeight(buf, aHelper, strings));
        Indexed<String> conditionJson = new Indexed<>(strings, buffer.readShortLE());
        long minPassingNeighbors = buffer.readUnsignedIntLE();
        return new BiomeConditionalTransformationData(weightedBiomes, conditionJson.get(), minPassingNeighbors);
    }

    protected void writeWeightedTemperature(ByteBuf buffer, BedrockCodecHelper helper, BiomeWeightedTemperatureData weightedTemperature) {
        VarInts.writeInt(buffer, weightedTemperature.temperature().ordinal());
        buffer.writeIntLE((int) weightedTemperature.weight());
    }

    protected BiomeWeightedTemperatureData readWeightedTemperature(ByteBuf buffer, BedrockCodecHelper helper) {
        BiomeTemperatureCategory temperature = TEMPERATURE_CATEGORIES[VarInts.readInt(buffer)];
        int weight = buffer.readIntLE();
        return new BiomeWeightedTemperatureData(temperature, weight);
    }

    protected void writeMultinoiseGenRules(ByteBuf buffer, BedrockCodecHelper helper, BiomeMultinoiseGenRulesData multinoiseGenRules) {
        buffer.writeFloatLE(multinoiseGenRules.temperature());
        buffer.writeFloatLE(multinoiseGenRules.humidity());
        buffer.writeFloatLE(multinoiseGenRules.altitude());
        buffer.writeFloatLE(multinoiseGenRules.weirdness());
        buffer.writeFloatLE(multinoiseGenRules.weight());
    }

    protected BiomeMultinoiseGenRulesData readMultinoiseGenRules(ByteBuf buffer, BedrockCodecHelper helper) {
        float temperature = buffer.readFloatLE();
        float humidity = buffer.readFloatLE();
        float altitude = buffer.readFloatLE();
        float weirdness = buffer.readFloatLE();
        float weight = buffer.readFloatLE();

        return new BiomeMultinoiseGenRulesData(temperature, humidity, altitude, weirdness, weight);
    }

    protected void writeLegacyWorldGenRules(ByteBuf buffer, BedrockCodecHelper helper, BiomeLegacyWorldGenRulesData legacyWorldGenRules, SequencedHashSet<String> strings) {
        helper.writeArray(buffer, legacyWorldGenRules.legacyPreHills(),
                (buf, aHelper, data) -> this.writeConditionalTransformation(buf, aHelper, data, strings));
    }

    protected BiomeLegacyWorldGenRulesData readLegacyWorldGenRules(ByteBuf buffer, BedrockCodecHelper helper,
                                                                   List<String> strings) {
        List<BiomeConditionalTransformationData> legacyPreHills = new ObjectArrayList<>();
        helper.readArray(buffer, legacyPreHills, (buf, aHelper) -> this.readConditionalTransformation(buf, aHelper, strings));
        return new BiomeLegacyWorldGenRulesData(legacyPreHills);
    }

    protected void writeBlock(ByteBuf buffer, BedrockCodecHelper helper, BlockDefinition blockDefinition) {
        if (blockDefinition == null) {
            buffer.writeIntLE(-1);
            return;
        }
        DefinitionUtils.checkDefinition(helper.getBlockDefinitions(), blockDefinition);
        buffer.writeIntLE(blockDefinition.runtimeId());
    }

    protected BlockDefinition readBlock(ByteBuf buffer, BedrockCodecHelper helper) {
        int runtimeId = buffer.readIntLE();
        if (runtimeId == -1) {
            return null;
        }
        return helper.getBlockDefinitions().getDefinition(runtimeId);
    }

    protected ExpressionOp readExpressionOp(ByteBuf buffer) {
        int index = VarInts.readInt(buffer);
        if (index == -1) {
            return null;
        }
        return EXPRESSION_OPS[index];
    }

    protected void writeExpressionOp(ByteBuf buffer, ExpressionOp expressionOp) {
        if (expressionOp == null) {
            VarInts.writeInt(buffer, -1);
            return;
        }
        VarInts.writeInt(buffer, expressionOp.ordinal());
    }
}
