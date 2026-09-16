package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;
import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.ServerboundDiagnosticsSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDiagnosticsPacket.EntityDiagnostics;
public class ServerboundDiagnosticsSerializer_v2193 extends ServerboundDiagnosticsSerializer_v2168 {
    public ServerboundDiagnosticsSerializer_v2193(org.cloudburstmc.protocol.bedrock.util.TypeMap<org.cloudburstmc.protocol.bedrock.data.MemoryCategoryCounter.Category> memoryCategoryTypes) {
        super(memoryCategoryTypes);
    }
    @Override
    protected EntityDiagnostics readEntityDiagnostics(ByteBuf buffer, BedrockCodecHelper helper) {
        EntityDiagnostics info = super.readEntityDiagnostics(buffer, helper);
        info.setPosition(helper.readVector3f(buffer));
        info.setDimension(helper.readString(buffer));
        return info;
    }
    @Override
    protected void writeEntityDiagnostics(ByteBuf buffer, BedrockCodecHelper helper, EntityDiagnostics info) {
        super.writeEntityDiagnostics(buffer, helper, info);
        helper.writeVector3f(buffer, info.getPosition());
        helper.writeString(buffer, info.getDimension());
    }
}
