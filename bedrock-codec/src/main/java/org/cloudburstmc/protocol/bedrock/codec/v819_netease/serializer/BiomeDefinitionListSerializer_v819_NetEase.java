package org.cloudburstmc.protocol.bedrock.codec.v819_netease.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v800.serializer.BiomeDefinitionListSerializer_v800;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionChunkGenData;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;
import org.cloudburstmc.protocol.bedrock.util.Preconditions;
import org.cloudburstmc.protocol.bedrock.util.SequencedHashSet;
import org.cloudburstmc.protocol.bedrock.util.VarInts;
import org.cloudburstmc.protocol.bedrock.util.index.IndexedList;

import java.awt.Color;
import java.util.List;

public class BiomeDefinitionListSerializer_v819_NetEase extends BiomeDefinitionListSerializer_v800 {
    public static final BiomeDefinitionListSerializer_v819_NetEase INSTANCE = new BiomeDefinitionListSerializer_v819_NetEase();

    @Override
    protected BiomeDefinitionData readDefinition(ByteBuf buffer, BedrockCodecHelper helper, List<String> strings) {
        Integer id = readDefinitionId(buffer, helper);
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
        buffer.readIntLE();
        helper.readString(buffer);

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
                (buf, aHelper) -> readDefinitionChunkGen(buf, aHelper, strings));

        return new BiomeDefinitionData(id, temperature, downfall, redSporeDensity, blueSporeDensity,
                ashDensity, whiteAshDensity, 0, depth, scale, mapWaterColor, rain, tags, chunkGenData);
    }

    @Override
    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, BiomeDefinitionData definition, SequencedHashSet<String> strings) {
        writeDefinitionId(buffer, helper, definition);
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
        buffer.writeIntLE(0);
        helper.writeString(buffer, "");

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
}
