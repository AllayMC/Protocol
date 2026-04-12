package org.cloudburstmc.protocol.bedrock.codec.v859.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v818.serializer.PrimitiveShapesSerializer_v818;
import org.cloudburstmc.protocol.bedrock.data.primitiveshape.*;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.awt.*;

public class PrimitiveShapesSerializer_v859 extends PrimitiveShapesSerializer_v818 {

    public static final PrimitiveShapesSerializer_v859 INSTANCE = new PrimitiveShapesSerializer_v859();

    protected int toPayloadType(PrimitiveShape.Type type) {
        return switch (type) {
            case null -> 0;
            case ARROW -> 1;
            case TEXT -> 2;
            case BOX -> 3;
            case LINE -> 4;
            case SPHERE, CIRCLE -> 5;
        };
    }

    @Override
    protected void writeShape(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShape shape) {
        writeCommonShapeData(buffer, helper, shape);

        PrimitiveShape.Type type = shape.getType();
        VarInts.writeUnsignedInt(buffer, toPayloadType(type));
        if (type == null) {
            return;
        }

        switch (type) {
            case ARROW:
                PrimitiveArrow arrow = (PrimitiveArrow) shape;
                helper.writeOptionalNull(buffer, arrow.getArrowEndPosition(), WRITE_VECTOR3F);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadLength(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadRadius(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadSegments(), ByteBuf::writeByte);
                break;
            case BOX:
                PrimitiveBox box = (PrimitiveBox) shape;
                helper.writeVector3f(buffer, box.getBoxBounds());
                break;
            case CIRCLE:
                PrimitiveCircle circle = (PrimitiveCircle) shape;
                buffer.writeByte(circle.getSegments());
                break;
            case LINE:
                PrimitiveLine line = (PrimitiveLine) shape;
                helper.writeVector3f(buffer, line.getLineEndPosition());
                break;
            case SPHERE:
                PrimitiveSphere sphere = (PrimitiveSphere) shape;
                buffer.writeByte(sphere.getSegments());
                break;
            case TEXT:
                PrimitiveText text = (PrimitiveText) shape;
                helper.writeString(buffer, text.getText());
                break;
        }
    }

    @Override
    protected void writeCommonShapeData(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShape shape) {
        super.writeCommonShapeData(buffer, helper, shape);

        VarInts.writeInt(buffer, shape.getDimension());
    }

    @Override
    protected PrimitiveShape readShape(ByteBuf buffer, BedrockCodecHelper helper) {
        long id = VarInts.readUnsignedLong(buffer);

        PrimitiveShape.Type type = helper.readOptional(buffer, null,
                (buf, aHelper) -> SHAPE_TYPES[buf.readUnsignedByte()]);
        Vector3f position = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float scale = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Vector3f rotation = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float totalTimeLeft = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Color color = helper.readOptional(buffer, null, READ_COLOR);

        int dimension = VarInts.readInt(buffer);
        int payloadType = VarInts.readUnsignedInt(buffer); // Unused

        if (type == null) {
            return new PrimitiveShape(id, dimension);
        }

        return switch (type) {
            case ARROW -> {
                Vector3f arrowEndPosition = helper.readOptional(buffer, null, READ_VECTOR3F);
                Float arrowHeadLength = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
                Float arrowHeadRadius = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
                Integer arrowHeadSegments = helper.readOptional(buffer, null, buf -> (int) buf.readUnsignedByte());
                yield new PrimitiveArrow(id, dimension, position, scale, rotation, totalTimeLeft, color, arrowEndPosition,
                        arrowHeadLength, arrowHeadRadius, arrowHeadSegments);
            }
            case BOX -> {
                Vector3f boxBounds = helper.readVector3f(buffer);
                yield new PrimitiveBox(id, dimension, position, scale, rotation, totalTimeLeft, color, boxBounds);
            }
            case CIRCLE -> {
                Integer circleSegments = (int) buffer.readUnsignedByte();
                yield new PrimitiveCircle(id, dimension, position, scale, rotation, totalTimeLeft, color, circleSegments);
            }
            case LINE -> {
                Vector3f lineEndPosition = helper.readVector3f(buffer);
                yield new PrimitiveLine(id, dimension, position, scale, rotation, totalTimeLeft, color, lineEndPosition);
            }
            case SPHERE -> {
                Integer sphereSegments = (int) buffer.readUnsignedByte();
                yield new PrimitiveSphere(id, dimension, position, scale, rotation, totalTimeLeft, color, sphereSegments);
            }
            case TEXT -> {
                String text = helper.readString(buffer);
                yield new PrimitiveText(id, dimension, position, scale, rotation, totalTimeLeft, color, text);
            }
        };
    }
}
