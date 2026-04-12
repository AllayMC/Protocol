package org.cloudburstmc.protocol.bedrock.data.primitiveshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents a primitive circle used in the Bedrock protocol.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PrimitiveCircle extends PrimitiveShape {

    /**
     * The segments.
     */
    private final Integer segments;

    public PrimitiveCircle(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                           @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                           Integer segments) {
        this(id, dimension, position, scale, rotation, totalTimeLeft, color, segments, null);
    }

    public PrimitiveCircle(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                           @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                           Integer segments, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, attachedToEntityId);
        this.segments = segments;
    }

    public PrimitiveCircle(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                           @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                           @Nullable Float maximumRenderDistance, Integer segments, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, maximumRenderDistance, attachedToEntityId);
        this.segments = segments;
    }

    @Override
    public Type getType() {
        return Type.CIRCLE;
    }
}
