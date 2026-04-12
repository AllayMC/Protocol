package org.cloudburstmc.protocol.bedrock.codec.v924.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v859.serializer.PrimitiveShapesSerializer_v859;
import org.cloudburstmc.protocol.bedrock.data.primitiveshape.*;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.awt.*;

public class PrimitiveShapesSerializer_v924 extends PrimitiveShapesSerializer_v859 {

    public static final PrimitiveShapesSerializer_v924 INSTANCE = new PrimitiveShapesSerializer_v924();

    @Override
    protected void writeCommonShapeData(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShape shape) {
        VarInts.writeUnsignedLong(buffer, shape.getId());
        helper.writeOptionalNull(buffer, shape.getType(), (buf, type) -> buf.writeByte(type.ordinal()));
        helper.writeOptionalNull(buffer, shape.getPosition(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getScale(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getRotation(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getTotalTimeLeft(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getColor(), WRITE_COLOR);

        helper.writeOptionalNull(buffer, shape.getDimension(), VarInts::writeInt);
        helper.writeOptionalNull(buffer, shape.getAttachedToEntityId(), VarInts::writeUnsignedLong);
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

        Integer dimension = helper.readOptional(buffer, -1, VarInts::readInt);
        Long attachedToEntityId = helper.readOptional(buffer, null, VarInts::readUnsignedLong);
        int payloadType = VarInts.readUnsignedInt(buffer); // Unused

        if (type == null) {
            return new PrimitiveShape(id, dimension);
        }

        switch (type) {
            case ARROW:
                Vector3f arrowEndPosition = helper.readOptional(buffer, null, READ_VECTOR3F);
                Float arrowHeadLength = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
                Float arrowHeadRadius = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
                Integer arrowHeadSegments = helper.readOptional(buffer, null, buf -> (int) buf.readUnsignedByte());
                return new PrimitiveArrow(id, dimension, position, scale, rotation, totalTimeLeft, color, arrowEndPosition,
                        arrowHeadLength, arrowHeadRadius, arrowHeadSegments, attachedToEntityId);
            case BOX:
                Vector3f boxBounds = helper.readVector3f(buffer);
                return new PrimitiveBox(id, dimension, position, scale, rotation, totalTimeLeft, color, boxBounds, attachedToEntityId);
            case CIRCLE:
                Integer circleSegments = (int) buffer.readUnsignedByte();
                return new PrimitiveCircle(id, dimension, position, scale, rotation, totalTimeLeft, color, circleSegments, attachedToEntityId);
            case LINE:
                Vector3f lineEndPosition = helper.readVector3f(buffer);
                return new PrimitiveLine(id, dimension, position, scale, rotation, totalTimeLeft, color, lineEndPosition, attachedToEntityId);
            case SPHERE:
                Integer sphereSegments = (int) buffer.readUnsignedByte();
                return new PrimitiveSphere(id, dimension, position, scale, rotation, totalTimeLeft, color, sphereSegments, attachedToEntityId);
            case TEXT:
                String text = helper.readString(buffer);
                return new PrimitiveText(id, dimension, position, scale, rotation, totalTimeLeft, color, text, attachedToEntityId);
            default:
                throw new IllegalStateException("Unknown primitive shape type");
        }
    }
}
