package org.cloudburstmc.protocol.bedrock.codec.v1001.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.ClientboundAttributeLayerSyncSerializer_v975;
import org.cloudburstmc.protocol.bedrock.data.attributelayer.*;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.util.ArrayList;
import java.util.List;

public class ClientboundAttributeLayerSyncSerializer_v1001 extends ClientboundAttributeLayerSyncSerializer_v975 {

    public static final ClientboundAttributeLayerSyncSerializer_v1001 INSTANCE = new ClientboundAttributeLayerSyncSerializer_v1001();

    @Override
    protected void writeUpdateAttributeLayers(ByteBuf buf, BedrockCodecHelper helper, UpdateAttributeLayersData data) {
        List<AttributeLayerData> layers = data.attributeLayers();
        helper.writeArray(buf, layers, (b, layer) -> {
            helper.writeString(b, layer.layerName());
            helper.writeOptionalNull(b, layer.noiseName(), helper::writeString);
            VarInts.writeInt(b, layer.dimension());
            writeAttributeLayerSettings(b, helper, layer.settings());
            helper.writeArray(b, layer.attributes(), (bb, attr) ->
                    writeEnvironmentAttribute(bb, helper, attr));
        });
    }

    @Override
    protected UpdateAttributeLayersData readUpdateAttributeLayers(ByteBuf buf, BedrockCodecHelper helper) {
        List<AttributeLayerData> layers = new ArrayList<>();
        helper.readArray(buf, layers, b -> {
            String name = helper.readStringMaxLen(buf, 128);
            String noise = helper.readOptional(b, null, helper::readString); // new
            int dim = VarInts.readInt(b);
            AttributeLayerSettings settings = readAttributeLayerSettings(b, helper);

            List<EnvironmentAttributeData> attrs = new ArrayList<>();
            helper.readArray(b, attrs, bb -> readEnvironmentAttribute(bb, helper), 1024);

            return new AttributeLayerData(name, noise, dim, settings, attrs);
        }, 512);
        return new UpdateAttributeLayersData(layers);
    }

    @Override
    protected void writeEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper, EnvironmentAttributeData e) {
        super.writeEnvironmentAttribute(buf, helper, e);

        buf.writeIntLE(e.localTransitionTicks());
        buf.writeBoolean(e.noiseTransition());
    }

    @Override
    protected EnvironmentAttributeData readEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper) {
        String name = helper.readStringMaxLen(buf, 128);

        AttributeData from = helper.readOptional(buf, null, b -> readAttributeData(b, helper));
        AttributeData attribute = readAttributeData(buf, helper);
        AttributeData to = helper.readOptional(buf, null, b -> readAttributeData(b, helper));

        int currentTicks = (int) buf.readUnsignedIntLE();
        int totalTicks = (int) buf.readUnsignedIntLE();

        CameraEase easing = CameraEase.fromName(helper.readString(buf));

        int localTransitionTicks = (int) buf.readUnsignedIntLE();
        boolean noiseTransition = buf.readBoolean();

        return new EnvironmentAttributeData(name, from, attribute, to, currentTicks, totalTicks, easing, localTransitionTicks, noiseTransition);
    }
}
