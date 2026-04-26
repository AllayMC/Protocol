package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v503.serializer.DimensionDataSerializer_v503;
import org.cloudburstmc.protocol.bedrock.data.definitions.DimensionDefinition;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DimensionDataSerializer_v975 extends DimensionDataSerializer_v503 {
    public static final DimensionDataSerializer_v975 INSTANCE = new DimensionDataSerializer_v975();

    @Override
    protected void writeDefinition(ByteBuf buffer, BedrockCodecHelper helper, DimensionDefinition definition) {
        super.writeDefinition(buffer, helper, definition);
        VarInts.writeInt(buffer, definition.dimensionType());
    }

    @Override
    protected DimensionDefinition readDefinition(ByteBuf buffer, BedrockCodecHelper helper) {
        String id = helper.readString(buffer);
        int maximumHeight = VarInts.readInt(buffer);
        int minimumHeight = VarInts.readInt(buffer);
        int generatorType = VarInts.readInt(buffer);
        int dimensionType = VarInts.readInt(buffer);
        return new DimensionDefinition(id, maximumHeight, minimumHeight, generatorType, dimensionType);
    }
}
