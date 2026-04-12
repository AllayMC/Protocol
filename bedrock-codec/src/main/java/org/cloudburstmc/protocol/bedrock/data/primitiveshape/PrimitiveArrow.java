package org.cloudburstmc.protocol.bedrock.data.primitiveshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents a primitive arrow used in the Bedrock protocol.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PrimitiveArrow extends PrimitiveShape {

    /**
     * The arrow end position.
     */
    private final Vector3f arrowEndPosition;
    /**
     * The arrow head length.
     */
    private final Float arrowHeadLength;
    /**
     * The arrow head radius.
     */
    private final Float arrowHeadRadius;
    /**
     * The arrow head segments.
     */
    private final Integer arrowHeadSegments;

    public PrimitiveArrow(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                          @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                          Vector3f arrowEndPosition, Float arrowHeadLength, Float arrowHeadRadius,
                          Integer arrowHeadSegments) {
        this(id, dimension, position, scale, rotation, totalTimeLeft, color, arrowEndPosition, arrowHeadLength,
                arrowHeadRadius, arrowHeadSegments, null);
    }

    public PrimitiveArrow(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                          @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                          Vector3f arrowEndPosition, Float arrowHeadLength, Float arrowHeadRadius,
                          Integer arrowHeadSegments, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, attachedToEntityId);
        this.arrowEndPosition = arrowEndPosition;
        this.arrowHeadLength = arrowHeadLength;
        this.arrowHeadRadius = arrowHeadRadius;
        this.arrowHeadSegments = arrowHeadSegments;
    }

    public PrimitiveArrow(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                          @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                          @Nullable Float maximumRenderDistance, Vector3f arrowEndPosition, Float arrowHeadLength,
                          Float arrowHeadRadius, Integer arrowHeadSegments, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, maximumRenderDistance, attachedToEntityId);
        this.arrowEndPosition = arrowEndPosition;
        this.arrowHeadLength = arrowHeadLength;
        this.arrowHeadRadius = arrowHeadRadius;
        this.arrowHeadSegments = arrowHeadSegments;
    }

    @Override
    public Type getType() {
        return Type.ARROW;
    }
}
