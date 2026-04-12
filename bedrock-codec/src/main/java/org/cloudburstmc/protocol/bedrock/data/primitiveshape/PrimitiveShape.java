package org.cloudburstmc.protocol.bedrock.data.primitiveshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Defines a single primitive shape rendered by {@link
 * org.cloudburstmc.protocol.bedrock.packet.PrimitiveShapesPacket}. Each shape has a unique network ID
 * and a set of optional parameters depending on its type.
 */
@Getter
@ToString
@EqualsAndHashCode
public class PrimitiveShape {

    /**
     * The network ID of the shape.
     */
    private final long id;
    /**
     * The optional location of the shape.
     */
    @Nullable
    private final Vector3f position;
    /**
     * The scale of the shape.
     */
    @Nullable
    private final Float scale;
    /**
     * The rotation of the shape.
     */
    @Nullable
    private final Vector3f rotation;
    /**
     * The total time left of the shape.
     */
    @Nullable
    private final Float totalTimeLeft;
    /**
     * The ARGB colour of the shape.
     */
    @Nullable
    private final Color color;
    /**
     * The optional maximum render distance of the shape.
     *
     * @since v974
     */
    @Nullable
    private final Float maximumRenderDistance;
    /**
     * The optional dimension ID where the shape should be rendered.
     *
     * @since v859
     */
    private final int dimension;
    /**
     * The optional runtime ID of the entity the shape is attached to.
     *
     * @since v924
     */
    @Nullable
    private final Long attachedToEntityId;

    public PrimitiveShape(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                          @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                          @Nullable Float maximumRenderDistance, @Nullable Long attachedToEntityId) {
        this.id = id;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.totalTimeLeft = totalTimeLeft;
        this.color = color;
        this.maximumRenderDistance = maximumRenderDistance;
        this.dimension = dimension;
        this.attachedToEntityId = attachedToEntityId;
    }

    public PrimitiveShape(long id) {
        this(id, 0, null, null, null, null, null, null, null);
    }

    public PrimitiveShape(long id, int dimension) {
        this(id, dimension, null, null, null, null, null, null, null);
    }

    public PrimitiveShape(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                          @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                          @Nullable Long attachedToEntityId) {
        this(id, dimension, position, scale, rotation, totalTimeLeft, color, null, attachedToEntityId);
    }

    public Type getType() {
        return null;
    }

    public enum Type {
        LINE,
        BOX,
        SPHERE,
        CIRCLE,
        TEXT,
        ARROW
    }
}
