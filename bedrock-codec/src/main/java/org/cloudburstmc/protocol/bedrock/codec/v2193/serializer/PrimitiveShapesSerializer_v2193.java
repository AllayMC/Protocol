package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v1001.serializer.PrimitiveShapesSerializer_v1001;
import org.cloudburstmc.protocol.bedrock.data.primitiveshape.PrimitiveText;

import java.awt.Color;

public class PrimitiveShapesSerializer_v2193 extends PrimitiveShapesSerializer_v1001 {

    public static final PrimitiveShapesSerializer_v2193 INSTANCE = new PrimitiveShapesSerializer_v2193();

    @Override
    protected PrimitiveText readText(ByteBuf buffer, BedrockCodecHelper helper, long id, int dimension,
                                     Vector3f position, Float scale, Vector3f rotation, Float totalTimeLeft,
                                     Color color, Float maximumRenderDistance, Long attachedToEntityId) {
        String text = helper.readString(buffer);
        boolean useRotation = buffer.readBoolean();
        Color background = helper.readOptional(buffer, null, buf -> new Color(buf.readIntLE(), true));
        float gap = buffer.readFloatLE();
        return new PrimitiveText(id, dimension, position, scale, rotation, totalTimeLeft, color, text,
                useRotation, background, buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(),
                maximumRenderDistance, attachedToEntityId, gap);
    }

    @Override
    protected void writeText(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveText text) {
        helper.writeString(buffer, text.getText());
        buffer.writeBoolean(text.isUseRotation());
        helper.writeOptionalNull(buffer, text.getBackgroundColor(), (buf, color) -> buf.writeIntLE(color.getRGB()));
        buffer.writeFloatLE(text.getLineGapHeight());
        buffer.writeBoolean(text.isDepthTest());
        buffer.writeBoolean(text.isShowBackface());
        buffer.writeBoolean(text.isShowTextBackface());
    }
}
