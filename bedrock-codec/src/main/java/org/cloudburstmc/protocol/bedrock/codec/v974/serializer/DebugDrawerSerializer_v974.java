package org.cloudburstmc.protocol.bedrock.codec.v974.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v924.serializer.DebugDrawerSerializer_v924;
import org.cloudburstmc.protocol.bedrock.data.debugshape.*;
import org.cloudburstmc.protocol.bedrock.packet.DebugDrawerPacket;
import org.cloudburstmc.protocol.bedrock.util.VarInts;

import java.awt.*;

public class DebugDrawerSerializer_v974 extends DebugDrawerSerializer_v924 {

    public static final DebugDrawerSerializer_v974 INSTANCE = new DebugDrawerSerializer_v974();

    @Override
    protected void writeShape(ByteBuf buffer, BedrockCodecHelper helper, DebugShape shape) {
        writeCommonShapeData(buffer, helper, shape);

        DebugShape.Type type = shape.getType();
        VarInts.writeUnsignedInt(buffer, toPayloadType(type));
        if (type == null) {
            return;
        }

        switch (type) {
            case ARROW -> {
                DebugArrow arrow = (DebugArrow) shape;
                helper.writeOptionalNull(buffer, arrow.getArrowEndPosition(), WRITE_VECTOR3F);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadLength(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadRadius(), ByteBuf::writeFloatLE);
                helper.writeOptionalNull(buffer, arrow.getArrowHeadSegments(), ByteBuf::writeByte);
            }
            case BOX -> {
                DebugBox box = (DebugBox) shape;
                helper.writeVector3f(buffer, box.getBoxBounds());
            }
            case CIRCLE -> {
                DebugCircle circle = (DebugCircle) shape;
                buffer.writeByte(circle.getSegments());
            }
            case LINE -> {
                DebugLine line = (DebugLine) shape;
                helper.writeVector3f(buffer, line.getLineEndPosition());
            }
            case SPHERE -> {
                DebugSphere sphere = (DebugSphere) shape;
                buffer.writeByte(sphere.getSegments());
            }
            case TEXT -> {
                DebugText text = (DebugText) shape;
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
    protected void writeCommonShapeData(ByteBuf buffer, BedrockCodecHelper helper, DebugShape shape) {
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
    protected DebugShape readShape(ByteBuf buffer, BedrockCodecHelper helper) {
        long id = VarInts.readUnsignedLong(buffer);

        DebugShape.Type type = helper.readOptional(buffer, null, (buf, aHelper) -> SHAPE_TYPES[buf.readUnsignedByte()]);
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
            return new DebugShape(id, dimension, position, scale, rotation, totalTimeLeft, color, maximumRenderDistance, attachedToEntityId);
        }

        return switch (type) {
            case ARROW -> new DebugArrow(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, helper.readOptional(buffer, null, READ_VECTOR3F),
                    helper.readOptional(buffer, null, ByteBuf::readFloatLE),
                    helper.readOptional(buffer, null, ByteBuf::readFloatLE),
                    helper.readOptional(buffer, null, buf -> (int) buf.readUnsignedByte()),
                    attachedToEntityId);
            case BOX -> new DebugBox(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, helper.readVector3f(buffer), attachedToEntityId);
            case CIRCLE -> new DebugCircle(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, (int) buffer.readUnsignedByte(), attachedToEntityId);
            case LINE -> new DebugLine(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, helper.readVector3f(buffer), attachedToEntityId);
            case SPHERE -> new DebugSphere(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    maximumRenderDistance, (int) buffer.readUnsignedByte(), attachedToEntityId);
            case TEXT -> new DebugText(id, dimension, position, scale, rotation, totalTimeLeft, color,
                    helper.readString(buffer), buffer.readBoolean(),
                    helper.readOptional(buffer, null, value -> new Color(value.readIntLE(), true)),
                    buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(),
                    maximumRenderDistance, attachedToEntityId);
        };
    }
}
