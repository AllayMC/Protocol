package org.cloudburstmc.protocol.bedrock.codec.v924.serializer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.ServerboundDiagnosticsSerializer_v712;
import org.cloudburstmc.protocol.bedrock.data.MemoryCategoryCounter;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDiagnosticsPacket;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;

@RequiredArgsConstructor
public class ServerboundDiagnosticsSerializer_v924 extends ServerboundDiagnosticsSerializer_v712 {
    private final TypeMap<MemoryCategoryCounter.Category> memoryCategoryTypes;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeArray(buffer, packet.getMemoryCategoryValues(), this::writeMemoryCategoryCounter);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.deserialize(buffer, helper, packet);
        helper.readArray(buffer, packet.getMemoryCategoryValues(), this::readMemoryCategoryCounter);
    }

    protected void writeMemoryCategoryCounter(ByteBuf buffer, BedrockCodecHelper helper, MemoryCategoryCounter counter) {
        buffer.writeByte(this.memoryCategoryTypes.getId(counter.category()));
        buffer.writeLongLE(counter.currentBytes());
    }

    protected MemoryCategoryCounter readMemoryCategoryCounter(ByteBuf buffer, BedrockCodecHelper helper) {
        return new MemoryCategoryCounter(this.memoryCategoryTypes.getType(buffer.readUnsignedByte()), buffer.readLongLE());
    }
}
