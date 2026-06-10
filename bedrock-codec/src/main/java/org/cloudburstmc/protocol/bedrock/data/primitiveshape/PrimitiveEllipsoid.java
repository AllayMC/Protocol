package org.cloudburstmc.protocol.bedrock.data.primitiveshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents a primitive ellipsoid used in the Bedrock protocol.
 *
 * @since v1001
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PrimitiveEllipsoid extends PrimitiveShape {

    /**
     * The number of segments per axis used to render the ellipsoid.
     */
    private final int segments;
    /**
     * The ellipsoid radii.
     */
    private final Vector3f radii;

    public PrimitiveEllipsoid(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                              @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                              @Nullable Float maximumRenderDistance, int segments, Vector3f radii,
                              @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, maximumRenderDistance, attachedToEntityId);
        this.segments = segments;
        this.radii = radii;
    }

    @Override
    public Type getType() {
        return Type.ELLIPSOID;
    }
}
