package org.cloudburstmc.protocol.bedrock.data.primitiveshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents a primitive cylinder used in the Bedrock protocol.
 *
 * @since v1001
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PrimitiveCylinder extends PrimitiveShape {

    /**
     * The height of the cylinder.
     */
    private final float height;
    /**
     * The number of segments used to render the cylinder.
     */
    private final int segments;
    /**
     * The cylinder radii on the X axis.
     */
    private final Vector2f radiusX;
    /**
     * The cylinder radii on the Z axis.
     */
    private final Vector2f radiusZ;

    public PrimitiveCylinder(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                             @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                             @Nullable Float maximumRenderDistance, float height, int segments,
                             Vector2f radiusX, Vector2f radiusZ, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, maximumRenderDistance, attachedToEntityId);
        this.height = height;
        this.segments = segments;
        this.radiusX = radiusX;
        this.radiusZ = radiusZ;
    }

    @Override
    public Type getType() {
        return Type.CYLINDER;
    }
}
