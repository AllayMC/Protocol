package org.cloudburstmc.protocol.bedrock.codec.v860_netease.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v859.serializer.BiomeDefinitionListSerializer_v859;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionChunkGenData;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;
import org.cloudburstmc.protocol.bedrock.util.Preconditions;
import org.cloudburstmc.protocol.bedrock.util.SequencedHashSet;
import org.cloudburstmc.protocol.bedrock.util.VarInts;
import org.cloudburstmc.protocol.bedrock.util.index.IndexedList;

import java.awt.Color;
import java.util.List;

/**
 * NetEase {@code v860} BiomeDefinitionList serializer.
 *
 * <p>The NetEase {@code v860} fork uses the vanilla {@code v844}/{@code v859} biome definition layout (it carries
 * {@code foliageSnow} and {@code BiomeReplacementData}, not the legacy spore/ash fields) but inserts two
 * NetEase-only fields between {@code rain} and {@code tags}:</p>
 * <ul>
 *   <li>{@code dimension} (LInt) — biome dimension id</li>
 *   <li>{@code vanilla} (String) — the vanilla biome identifier this entry is derived from</li>
 * </ul>
 * <p>Both fields were introduced in {@code 1.21.90-netease} and are confirmed by both the Nukkit-MOT and
 * SynapseAPI ({@code BiomeDefinitionListPacket121111}) China client implementations. Since
 * {@link BiomeDefinitionData} has no components for them, the values are <b>read and discarded</b> on decode
 * and written as defaults ({@code 0} / {@code ""}) on encode — the same strategy used by
 * {@link org.cloudburstmc.protocol.bedrock.codec.v819_netease.serializer.BiomeDefinitionListSerializer_v819_NetEase}.</p>
 *
 * <p>{@link BiomeDefinitionListSerializer_v859} is the base class because it already provides the
 * {@code foliageSnow} definition layout (inherited from {@code v844}) and the {@code BiomeReplacementData}
 * handling in {@code readDefinitionChunkGen}/{@code writeDefinitionChunkGen}.</p>
 *
 * <p><b>Known limitation</b> (out of scope here, tracked separately): the inherited {@code v859}
 * {@code chunkGenData} trailer serializes {@code BiomeReplacementData} as a single optional object with
 * {@code short} biome/dimension fields, whereas the NetEase {@code 860} client (per the SynapseAPI
 * {@code BiomeDefinitionListPacket121111} implementation) writes a {@code List<BiomeReplacementData>} with
 * string-pooled biome/dimension fields. This only affects biomes that carry a non-null {@code chunkGenData}
 * containing replacement data; biomes without {@code chunkGenData} (the common case) are unaffected.</p>
 */
public class BiomeDefinitionListSerializer_v860_NetEase extends BiomeDefinitionListSerializer_v859 {
    public static final BiomeDefinitionListSerializer_v860_NetEase INSTANCE = new BiomeDefinitionListSerializer_v860_NetEase();

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

        // NetEase-only fields (since 1.21.90-netease): dimension (LInt) + vanilla (String).
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
                (buf, aHelper) -> this.readDefinitionChunkGen(buf, aHelper, strings));

        return new BiomeDefinitionData(id, temperature, downfall, 0, 0, 0, 0, foliageSnow, depth, scale,
                mapWaterColor, rain, tags, chunkGenData);
    }

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

        // NetEase-only fields (since 1.21.90-netease): dimension (LInt) + vanilla (String).
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
