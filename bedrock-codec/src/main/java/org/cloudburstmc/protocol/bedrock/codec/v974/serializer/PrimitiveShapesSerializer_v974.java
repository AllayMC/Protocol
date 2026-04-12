package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v924.serializer.PrimitiveShapesSerializer_v924;
import org.cloudburstmc.protocol.bedrock.data.primitiveshape.*;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.awt.*;

public class PrimitiveShapesSerializer_v974 extends PrimitiveShapesSerializer_v924 {

    public static final PrimitiveShapesSerializer_v974 INSTANCE = new PrimitiveShapesSerializer_v974();

    @Override
    protected void writeShape(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShape shape) {
        writeCommonShapeData(buffer, helper, shape);

        PrimitiveShape.Type type = shape.getType();
        VarInts.writeUnsignedInt(buffer, toPayloadType(type));
        if (type == null) {
            return;
        }

        switch (type) {
            case ARROW -> {
                PrimitiveArrow arrow = (PrimitiveArrow) shape;
                helper.writeOptionalNull(buffer, arrow.getArrowEndPosition(), WRITE_VECTOR3F);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadLength(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadRadius(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadSegments(), ByteBuf::writeByte);
            }
            case BOX -> {
                PrimitiveBox box = (PrimitiveBox) shape;
                helper.writeVector3f(buffer, box.getBoxBounds());
            }
            case CIRCLE -> {
                PrimitiveCircle circle = (PrimitiveCircle) shape;
                buffer.writeByte(circle.getSegments());
            }
            case LINE -> {
                PrimitiveLine line = (PrimitiveLine) shape;
                helper.writeVector3f(buffer, line.getLineEndPosition());
            }
            case SPHERE -> {
                PrimitiveSphere sphere = (PrimitiveSphere) shape;
                buffer.writeByte(sphere.getSegments());
            }
            case TEXT -> {
                PrimitiveText text = (PrimitiveText) shape;
                helper.writeString(buffer, text.getText());
                buffer.writeBoolean(text.isUseRotation());
                helper.writeOptionalNull(buffer, text.getBackgroundColor(), (buf, color) -> buf.writeIntLE(color.getRGB()));
                buffer.writeBoolean(text.isDepthTest());
                buffer.writeBoolean(text.isShowBackface());
                buffer.writeBoolean(text.isShowTextBackface());
            }
        }
    }

    @Override
    protected void writeCommonShapeData(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShape shape) {
        VarInts.writeUnsignedLong(buffer, shape.getId());
        helper.writeOptionalNull(buffer, shape.getType(), (buf, type) -> buf.writeByte(type.ordinal()));
        helper.writeOptionalNull(buffer, shape.getPosition(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getScale(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getRotation(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getTotalTimeLeft(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getMaximumRenderDistance(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getColor(), WRITE_COLOR);
        helper.writeOptionalNull(buffer, shape.getDimension(), VarInts::writeInt);
        helper.writeOptionalNull(buffer, shape.getAttachedToEntityId(), VarInts::writeLong);
    }

    @Override
    protected PrimitiveShape readShape(ByteBuf buffer, BedrockCodecHelper helper) {
        long id = VarInts.readUnsignedLong(buffer);

        PrimitiveShape.Type type = helper.readOptional(buffer, null, (buf, aHelper) -> SHAPE_TYPES[buf.readUnsignedByte()]);
        Vector3f position = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float scale = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Vector3f rotation = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float totalTimeLeft = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float maximumRenderDistance = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Color color = helper.readOptional(buffer, null, value -> new Color(value.readIntLE(), true));

        Integer dimension = helper.readOptional(buffer, -1, VarInts::readInt);
        Long attachedToEntityId = helper.readOptional(buffer, null, VarInts::readLong);
        VarInts.readUnsignedInt(buffer);

        if (type == null) {
            return new PrimitiveShape(id, dimension, position, scale, rotation, totalTimeLeft, color, maximumRenderDistance, attachedToEntityId);
        }

        return switch (type) {
            case ARROW -> new PrimitiveArrow(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, helper.readOptional(buffer, null, READ_VECTOR3F),
                    helper.readOptional(buffer, null, ByteBuf::readFloatLE),
                    helper.readOptional(buffer, null, ByteBuf::readFloatLE),
                    helper.readOptional(buffer, null, buf -> (int) buf.readUnsignedByte()),
                    attachedToEntityId);
            case BOX -> new PrimitiveBox(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, helper.readVector3f(buffer), attachedToEntityId);
            case CIRCLE -> new PrimitiveCircle(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, (int) buffer.readUnsignedByte(), attachedToEntityId);
            case LINE -> new PrimitiveLine(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, helper.readVector3f(buffer), attachedToEntityId);
            case SPHERE -> new PrimitiveSphere(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, (int) buffer.readUnsignedByte(), attachedToEntityId);
            case TEXT -> new PrimitiveText(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    helper.readString(buffer), buffer.readBoolean(),
                    helper.readOptional(buffer, null, value -> new Color(value.readIntLE(), true)),
                    buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(),
                    maximumRenderDistance, attachedToEntityId);
        };
    }
}
