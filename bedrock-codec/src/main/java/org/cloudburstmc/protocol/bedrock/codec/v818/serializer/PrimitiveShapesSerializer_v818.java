package org.cloudburstmc.protocol.bedrock.codec.v818.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.primitiveshape.*;
import org.cloudburstmc.protocol.bedrock.packet.PrimitiveShapesPacket;
import org.cloudburstmc.protocol.bedrock.util.TriConsumer;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PrimitiveShapesSerializer_v818 implements BedrockPacketSerializer<PrimitiveShapesPacket> {

    public static final PrimitiveShapesSerializer_v818 INSTANCE = new PrimitiveShapesSerializer_v818();

    protected static final PrimitiveShape.Type[] SHAPE_TYPES = PrimitiveShape.Type.values();

    protected static final TriConsumer<ByteBuf, BedrockCodecHelper, Vector3f> WRITE_VECTOR3F =
            (buffer, helper, vector3f) -> helper.writeVector3f(buffer, vector3f);
    protected static final BiConsumer<ByteBuf, Color> WRITE_COLOR =
            (buffer, color) -> buffer.writeIntLE(color.getRGB());
    protected static final TriConsumer<ByteBuf, BedrockCodecHelper, String> WRITE_STRING =
            (buffer, helper, text) -> helper.writeString(buffer, text);
    protected static final BiFunction<ByteBuf, BedrockCodecHelper, Vector3f> READ_VECTOR3F =
            (buffer, helper) -> helper.readVector3f(buffer);
    protected static final Function<ByteBuf, Color> READ_COLOR =
            buffer -> new Color(buffer.readIntLE());
    protected static final BiFunction<ByteBuf, BedrockCodecHelper, String> READ_STRING =
            (buffer, helper) -> helper.readString(buffer);

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShapesPacket packet) {
        helper.writeArray(buffer, packet.getShapes(), this::writeShape);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShapesPacket packet) {
        helper.readArray(buffer, packet.getShapes(), this::readShape);
    }

    protected void writeShape(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShape shape) {
        writeCommonShapeData(buffer, helper, shape);

        switch (shape.getType()) {
            case ARROW:
                PrimitiveArrow arrow = (PrimitiveArrow) shape;
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, arrow.getArrowEndPosition(), WRITE_VECTOR3F);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadLength(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadRadius(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadSegments(), ByteBuf::writeByte);
                break;
            case BOX:
                PrimitiveBox box = (PrimitiveBox) shape;
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, box.getBoxBounds(), WRITE_VECTOR3F);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                break;
            case CIRCLE:
                PrimitiveCircle circle = (PrimitiveCircle) shape;
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, circle.getSegments(), ByteBuf::writeByte);
                break;
            case LINE:
                PrimitiveLine line = (PrimitiveLine) shape;
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, line.getLineEndPosition(), WRITE_VECTOR3F);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                break;
            case SPHERE:
                PrimitiveSphere sphere = (PrimitiveSphere) shape;
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                helper.writeOptionalNull(buffer, sphere.getSegments(), ByteBuf::writeByte);
                break;
            case TEXT:
                PrimitiveText text = (PrimitiveText) shape;
                helper.writeOptionalNull(buffer, text.getText(), WRITE_STRING);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                buffer.writeBoolean(false);
                break;
        }
    }

    protected void writeCommonShapeData(ByteBuf buffer, BedrockCodecHelper helper, PrimitiveShape shape) {
        VarInts.writeUnsignedLong(buffer, shape.getId());
        helper.writeOptionalNull(buffer, shape.getType(), (buf, type) -> buf.writeByte(type.ordinal()));
        helper.writeOptionalNull(buffer, shape.getPosition(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getScale(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getRotation(), WRITE_VECTOR3F);
        helper.writeOptionalNull(buffer, shape.getTotalTimeLeft(), ByteBuf::writeFloatLE);
        helper.writeOptionalNull(buffer, shape.getColor(), WRITE_COLOR);
    }

    protected PrimitiveShape readShape(ByteBuf buffer, BedrockCodecHelper helper) {
        long id = VarInts.readUnsignedLong(buffer);

        PrimitiveShape.Type type = helper.readOptional(buffer, null,
                (buf, aHelper) -> SHAPE_TYPES[buf.readUnsignedByte()]);
        Vector3f position = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float scale = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Vector3f rotation = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float totalTimeLeft = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Color color = helper.readOptional(buffer, null, READ_COLOR);

        String text = helper.readOptional(buffer, null, READ_STRING);
        Vector3f boxBounds = helper.readOptional(buffer, null, READ_VECTOR3F);
        Vector3f lineEndPosition = helper.readOptional(buffer, null, READ_VECTOR3F);
        Float arrowHeadLength = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Float arrowHeadRadius = helper.readOptional(buffer, null, ByteBuf::readFloatLE);
        Integer segments = helper.readOptional(buffer, null, buf -> (int) buf.readUnsignedByte());

        if (type == null) {
            return new PrimitiveShape(id);
        }

        switch (type) {
            case ARROW:
                return new PrimitiveArrow(id, 0, position, scale, rotation, totalTimeLeft, color, lineEndPosition, arrowHeadLength, arrowHeadRadius, segments);
            case BOX:
                return new PrimitiveBox(id, 0, position, scale, rotation, totalTimeLeft, color, boxBounds);
            case CIRCLE:
                return new PrimitiveCircle(id, 0, position, scale, rotation, totalTimeLeft, color, segments);
            case LINE:
                return new PrimitiveLine(id, 0, position, scale, rotation, totalTimeLeft, color, lineEndPosition);
            case SPHERE:
                return new PrimitiveSphere(id, 0, position, scale, rotation, totalTimeLeft, color, segments);
            case TEXT:
                return new PrimitiveText(id, 0, position, scale, rotation, totalTimeLeft, color, text);
            default:
                throw new IllegalStateException("Unknown primitive shape type");
        }
    }
}
