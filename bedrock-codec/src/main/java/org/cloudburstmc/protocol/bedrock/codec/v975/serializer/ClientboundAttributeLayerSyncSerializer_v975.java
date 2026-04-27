package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v944.serializer.ClientboundAttributeLayerSyncSerializer_v944;
import org.cloudburstmc.protocol.bedrock.data.attributelayer.AttributeLayerSettings;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientboundAttributeLayerSyncSerializer_v975 extends ClientboundAttributeLayerSyncSerializer_v944 {
    public static final ClientboundAttributeLayerSyncSerializer_v975 INSTANCE = new ClientboundAttributeLayerSyncSerializer_v975();

    @Override
    protected void writeWeight(ByteBuf buf, BedrockCodecHelper helper, AttributeLayerSettings.Weight weight) {
        buf.writeFloatLE(((AttributeLayerSettings.FloatWeight) weight).value());
    }

    @Override
    protected AttributeLayerSettings.Weight readWeight(ByteBuf buf, BedrockCodecHelper helper) {
        return new AttributeLayerSettings.FloatWeight(buf.readFloatLE());
    }
}
