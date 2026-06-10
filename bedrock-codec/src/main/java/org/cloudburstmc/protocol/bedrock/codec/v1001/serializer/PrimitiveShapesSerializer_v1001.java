package org.cloudburstmc.protocol.bedrock.codec.v1001.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.PrimitiveShapesSerializer_v975;
import org.cloudburstmc.protocol.bedrock.data.primitiveshape.*;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.awt.*;

public class PrimitiveShapesSerializer_v1001 extends PrimitiveShapesSerializer_v975 {

    public static final PrimitiveShapesSerializer_v1001 INSTANCE = new PrimitiveShapesSerializer_v1001();

    @Override
    protected void writeShape(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShape shape) {
        PrimitiveShape.Type type = shape.getType();
        if (type == null || switch (type) {
            case ARROW, BOX, CIRCLE, LINE, SPHERE, TEXT -> true;
            default -> false;
        }) {
            super.writeShape(buffer, helper, shape);
            return;
        }

        writeCommonShapeData(buffer, helper, shape);
        VarInts.writeUnsignedInt(buffer, toPayloadType(type));

        switch (type) {
            case CYLINDER -> {
                PrimitiveCylinder cylinder = (PrimitiveCylinder) shape;
                helper.writeVector2f(buffer, cylinder.getRadiusX());
                helper.writeVector2f(buffer, cylinder.getRadiusZ());
                buffer.writeFloatLE(cylinder.getHeight());
                buffer.writeByte(cylinder.getSegments());
            }
            case PYRAMID -> {
                PrimitivePyramid pyramid = (PrimitivePyramid) shape;
                buffer.writeFloatLE(pyramid.getWidth());
                helper.writeOptionalNull(buffer, pyramid.getDepth(), ByteBuf::writeFloatLE);
                buffer.writeFloatLE(pyramid.getHeight());
            }
            case ELLIPSOID -> {
                PrimitiveEllipsoid ellipsoid = (PrimitiveEllipsoid) shape;
                helper.writeVector3f(buffer, ellipsoid.getRadii());
                buffer.writeByte(ellipsoid.getSegments());
            }
            case CONE -> {
                PrimitiveCone cone = (PrimitiveCone) shape;
                helper.writeVector2f(buffer, cone.getRadii());
                buffer.writeFloatLE(cone.getHeight());
                buffer.writeByte(cone.getSegments());
            }
            default -> throw new IllegalStateException("Unknown primitive shape type");
        }
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
            case CYLINDER -> {
                var radiusX = helper.readVector2f(buffer);
                var radiusZ = helper.readVector2f(buffer);
                yield new PrimitiveCylinder(id, dimension, position, scale, rotation, totalTimeLeft, color,
                        maximumRenderDistance, buffer.readFloatLE(), buffer.readUnsignedByte(),
                        radiusX, radiusZ, attachedToEntityId);
            }
            case PYRAMID -> {
                float width = buffer.readFloatLE();
                Float depth = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
                yield new PrimitivePyramid(id, dimension, position, scale, rotation, totalTimeLeft, color,
                        maximumRenderDistance, buffer.readFloatLE(), width, depth, attachedToEntityId);
            }
            case ELLIPSOID -> {
                var radii = helper.readVector3f(buffer);
                yield new PrimitiveEllipsoid(id, dimension, position, scale, rotation, totalTimeLeft, color,
                        maximumRenderDistance, buffer.readUnsignedByte(), radii, attachedToEntityId);
            }
            case CONE -> {
                var radii = helper.readVector2f(buffer);
                yield new PrimitiveCone(id, dimension, position, scale, rotation, totalTimeLeft, color,
                        maximumRenderDistance, buffer.readFloatLE(), buffer.readUnsignedByte(),
                        radii, attachedToEntityId);
            }
        };
    }

    @Override
    protected int toPayloadType(PrimitiveShape.Type type) {
        return switch (type) {
            case null -> 0;
            case ARROW -> 1;
            case TEXT -> 2;
            case BOX -> 3;
            case LINE -> 4;
            case SPHERE, CIRCLE -> 5;
            case CYLINDER -> 6;
            case PYRAMID -> 7;
            case ELLIPSOID -> 8;
            case CONE -> 9;
        };
    }
}
