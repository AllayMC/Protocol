package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2193.serializer.ClientboundAttributeLayerSyncSerializer_v2193;
import org.cloudburstmc.protocol.bedrock.data.attributelayer.*;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.util.ArrayList;
import java.util.List;

public class ClientboundAttributeLayerSyncSerializer_v2225 extends ClientboundAttributeLayerSyncSerializer_v2193 {

    public static final ClientboundAttributeLayerSyncSerializer_v2225 INSTANCE = new ClientboundAttributeLayerSyncSerializer_v2225();


    @Override
    protected void writeUpdateAttributeLayers(ByteBuf buf, BedrockCodecHelper helper, UpdateAttributeLayersData data) {
        List<AttributeLayerData> layers = data.attributeLayers();
        helper.writeArray(buf, layers, (b, layer) -> {
            helper.writeString(b, layer.layerName());
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
            int dim = VarInts.readInt(b);
            AttributeLayerSettings settings = readAttributeLayerSettings(b, helper);

            List<EnvironmentAttributeData> attrs = new ArrayList<>();
            helper.readArray(b, attrs, bb -> readEnvironmentAttribute(bb, helper), 1024);

            return new AttributeLayerData(name, null, dim, settings, attrs);
        }, 512);
        return new UpdateAttributeLayersData(layers);
    }

    @Override
    protected void writeEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper, EnvironmentAttributeData e) {
        helper.writeString(buf, e.attributeName());

        if (e.noiseTransition()) {
            VarInts.writeUnsignedInt(buf, 2); // NoiseTransitionAttributeData

            writeAttributeData(buf, helper, e.from());
            writeAttributeData(buf, helper, e.to());

            VarInts.writeUnsignedInt(buf, e.TotalTransitionTicks());
            VarInts.writeUnsignedInt(buf, e.CurrentTransitionTicks());

            VarInts.writeInt(buf, e.easing().ordinal());

            helper.writeString(buf, e.clockName());

            VarInts.writeUnsignedInt(buf, e.localTransitionTicks());

            helper.writeString(buf, e.noiseName());

            buf.writeByte(e.noiseAlignment().type().ordinal());
            VarInts.writeUnsignedInt(buf, e.noiseAlignment().value());
        } else if (e.from() != null && e.to() != null) {
            VarInts.writeUnsignedInt(buf, 1); // TransitionAttributeData

            writeAttributeData(buf, helper, e.from());
            writeAttributeData(buf, helper, e.to());

            VarInts.writeUnsignedInt(buf, e.TotalTransitionTicks());
            VarInts.writeUnsignedInt(buf, e.CurrentTransitionTicks());

            VarInts.writeInt(buf, e.easing().ordinal());

            helper.writeString(buf, e.clockName());
        } else {
            VarInts.writeUnsignedInt(buf, 0); // ConstantAttributeData
            writeAttributeData(buf, helper, e.attribute());
        }
    }

    @Override
    protected EnvironmentAttributeData readEnvironmentAttribute(ByteBuf buf, BedrockCodecHelper helper) {
        String name = helper.readStringMaxLen(buf, 128);

        int type = VarInts.readUnsignedInt(buf);

        if (type == 2) {
            AttributeData from = readAttributeData(buf, helper);
            AttributeData to = readAttributeData(buf, helper);

            int totalTransitionTicks = VarInts.readUnsignedInt(buf);
            int currentTransitionTicks = VarInts.readUnsignedInt(buf);

            CameraEase easing = CameraEase.values()[VarInts.readInt(buf)];

            String clockName = helper.readString(buf);

            int localTransitionTicks = VarInts.readUnsignedInt(buf);

            String noiseName = helper.readString(buf);

            NoiseAlignment na = new NoiseAlignment(NoiseAlignment.Type.values()[buf.readUnsignedByte()], VarInts.readUnsignedInt(buf));

            return new EnvironmentAttributeData(name, from, null, to, currentTransitionTicks, totalTransitionTicks, easing, clockName, localTransitionTicks, true, noiseName, na);
        } else if (type == 1) {
            AttributeData from = readAttributeData(buf, helper);
            AttributeData to = readAttributeData(buf, helper);

            int totalTransitionTicks = VarInts.readUnsignedInt(buf);
            int currentTransitionTicks = VarInts.readUnsignedInt(buf);

            CameraEase easing = CameraEase.values()[VarInts.readInt(buf)];

            String clockName = helper.readString(buf);

            return new EnvironmentAttributeData(name, from, null, to, currentTransitionTicks, totalTransitionTicks, easing, clockName, 0, false, null, null);
        } if (type == 0) {
            AttributeData attribute = readAttributeData(buf, helper);
            return new EnvironmentAttributeData(name, null, attribute, null, 0, 0, null, null, 0, false, null, null);
        } else {
            throw new IllegalArgumentException(type + " is not oneOf<ConstantAttributeData, TransitionAttributeData, NoiseTransitionAttributeData>");
        }
    }
}
