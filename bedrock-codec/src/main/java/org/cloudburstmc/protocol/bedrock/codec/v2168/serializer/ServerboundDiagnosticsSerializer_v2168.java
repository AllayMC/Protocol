package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.ServerboundDiagnosticsSerializer_v975;
import org.cloudburstmc.protocol.bedrock.data.MemoryCategoryCounter;
import org.cloudburstmc.protocol.bedrock.data.diagnostics.SystemCategory;
import org.cloudburstmc.protocol.bedrock.data.diagnostics.WhiskerScopeDataSummary;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDiagnosticsPacket;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;

public class ServerboundDiagnosticsSerializer_v2168 extends ServerboundDiagnosticsSerializer_v975 { // 975 intentional, system before whisker

    public ServerboundDiagnosticsSerializer_v2168(TypeMap<MemoryCategoryCounter.Category> memoryCategoryTypes) {
        super(memoryCategoryTypes);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeArray(buffer, packet.getSystemCategories(), ((buf, h, info) -> {
            helper.writeString(buf, info.categoryName());
            buf.writeLongLE(info.systemIndex());
        }));

        helper.writeArray(buffer, packet.getWhiskerScopes(), ((buf, h, info) -> {
            helper.writeString(buf, info.label());
            helper.writeString(buf, info.indentation());
            buf.writeLongLE(info.totalHighCostNS());
            buf.writeLongLE(info.totalMidCostNS());
            buf.writeLongLE(info.totalLowCostNS());
        }));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.deserialize(buffer, helper, packet);

        helper.readArray(buffer, packet.getSystemCategories(), (buf, h) ->
                new SystemCategory(
                        helper.readString(buf),
                        buf.readLongLE()));

        helper.readArray(buffer, packet.getWhiskerScopes(), (buf, h) ->
                new WhiskerScopeDataSummary(
                        helper.readString(buf),
                        helper.readString(buf),
                        buf.readLongLE(),
                        buf.readLongLE(),
                        buf.readLongLE()));
    }
}
