package org.cloudburstmc.protocol.bedrock.codec.v1001.serializer;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.BiomeDefinitionListSerializer_v975;
import org.cloudburstmc.protocol.bedrock.data.biome.*;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

import java.util.List;

public class BiomeDefinitionListSerializer_v1001 extends BiomeDefinitionListSerializer_v975 {

    public static final BiomeDefinitionListSerializer_v1001 INSTANCE = new BiomeDefinitionListSerializer_v1001();

    @Override
    protected void writeBiomeNoiseGradientSurfaceData(ByteBuf buffer, BedrockCodecHelper helper, BiomeNoiseGradientSurfaceData data) {
        helper.writeArray(buffer, data.nonReplaceableBlocks(), this::writeBlock);
        helper.writeArray(buffer, data.gradientBlocks(), (buf, h, val) -> {
            helper.writeString(buf, val.noise());
            buf.writeFloatLE(val.threshold());
            buf.writeFloatLE(val.rangeMin());
            buf.writeFloatLE(val.rangeMax());
            this.writeBlock(buf, h, val.block());
        });
        helper.writeString(buffer, data.noise());
        buffer.writeIntLE(data.firstOctave());
        helper.writeArray(buffer, data.amplitudes(), ByteBuf::writeFloatLE);
    }

    @Override
    protected BiomeNoiseGradientSurfaceData readBiomeNoiseGradientSurfaceData(ByteBuf buffer, BedrockCodecHelper helper) {
        List<BlockDefinition> nonReplaceableBlocks = new ObjectArrayList<>();
        helper.readArray(buffer, nonReplaceableBlocks, this::readBlock);
        List<NoiseBlockSpecifier> gradientBlocks = new ObjectArrayList<>();
        helper.readArray(buffer, gradientBlocks, (buf, h) ->
                new NoiseBlockSpecifier(helper.readString(buf), buf.readFloatLE(), buf.readFloatLE(), buf.readFloatLE(), this.readBlock(buf, h)));
        String noiseSeedString = helper.readString(buffer);
        int firstOctave = buffer.readIntLE();
        List<Float> amplitudes = new ObjectArrayList<>();
        helper.readArray(buffer, amplitudes, (buf, h) -> buf.readFloatLE());
        return new BiomeNoiseGradientSurfaceData(nonReplaceableBlocks, gradientBlocks, noiseSeedString, firstOctave, amplitudes);
    }
}
