package org.cloudburstmc.protocol.bedrock.data.primitiveshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents a primitive line used in the Bedrock protocol.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PrimitiveLine extends PrimitiveShape {

    /**
     * The line end position.
     */
    private final Vector3f lineEndPosition;

    public PrimitiveLine(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                         @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                         Vector3f lineEndPosition) {
        this(id, dimension, position, scale, rotation, totalTimeLeft, color, lineEndPosition, null);
    }

    public PrimitiveLine(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                         @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                         Vector3f lineEndPosition, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, attachedToEntityId);
        this.lineEndPosition = lineEndPosition;
    }

    public PrimitiveLine(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                         @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                         @Nullable Float maximumRenderDistance, Vector3f lineEndPosition, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, maximumRenderDistance, attachedToEntityId);
        this.lineEndPosition = lineEndPosition;
    }

    @Override
    public Type getType() {
        return Type.LINE;
    }
}
