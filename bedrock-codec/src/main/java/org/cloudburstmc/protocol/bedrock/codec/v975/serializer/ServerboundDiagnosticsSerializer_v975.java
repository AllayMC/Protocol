package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v924.serializer.ServerboundDiagnosticsSerializer_v924;
import org.cloudburstmc.protocol.bedrock.data.MemoryCategoryCounter;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDiagnosticsPacket;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;

public class ServerboundDiagnosticsSerializer_v975 extends ServerboundDiagnosticsSerializer_v924 {
    public ServerboundDiagnosticsSerializer_v975(TypeMap<MemoryCategoryCounter.Category> memoryCategoryTypes) {
        super(memoryCategoryTypes);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.serialize(buffer, helper, packet);
        helper.writeArray(buffer, packet.getEntityDiagnostics(), this::writeEntityDiagnostics);
        helper.writeArray(buffer, packet.getSystemDiagnostics(), this::writeSystemDiagnostics);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.deserialize(buffer, helper, packet);
        helper.readArray(buffer, packet.getEntityDiagnostics(), this::readEntityDiagnostics);
        helper.readArray(buffer, packet.getSystemDiagnostics(), this::readSystemDiagnostics);
    }

    private void writeEntityDiagnostics(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket.EntityDiagnostics diagnostics) {
        helper.writeString(buffer, diagnostics.getDisplayName());
        helper.writeString(buffer, diagnostics.getEntity());
        buffer.writeLongLE(diagnostics.getTimeInNs());
        buffer.writeByte(diagnostics.getPercentOfTotal());
    }

    private ServerboundDiagnosticsPacket.EntityDiagnostics readEntityDiagnostics(ByteBuf buffer, BedrockCodecHelper helper) {
        ServerboundDiagnosticsPacket.EntityDiagnostics diagnostics = new ServerboundDiagnosticsPacket.EntityDiagnostics();
        diagnostics.setDisplayName(helper.readString(buffer));
        diagnostics.setEntity(helper.readString(buffer));
        diagnostics.setTimeInNs(buffer.readLongLE());
        diagnostics.setPercentOfTotal(buffer.readUnsignedByte());
        return diagnostics;
    }

    private void writeSystemDiagnostics(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket.SystemDiagnostics diagnostics) {
        helper.writeString(buffer, diagnostics.getDisplayName());
        buffer.writeLongLE(diagnostics.getSystemIndex());
        buffer.writeLongLE(diagnostics.getTimeInNs());
        buffer.writeByte(diagnostics.getPercentOfTotal());
    }

    private ServerboundDiagnosticsPacket.SystemDiagnostics readSystemDiagnostics(ByteBuf buffer, BedrockCodecHelper helper) {
        ServerboundDiagnosticsPacket.SystemDiagnostics diagnostics = new ServerboundDiagnosticsPacket.SystemDiagnostics();
        diagnostics.setDisplayName(helper.readString(buffer));
        diagnostics.setSystemIndex(buffer.readLongLE());
        diagnostics.setTimeInNs(buffer.readLongLE());
        diagnostics.setPercentOfTotal(buffer.readUnsignedByte());
        return diagnostics;
    }
}
