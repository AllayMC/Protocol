package org.cloudburstmc.protocol.bedrock.data.primitiveshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents a primitive pyramid used in the Bedrock protocol.
 *
 * @since v1001
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PrimitivePyramid extends PrimitiveShape {

    /**
     * The height of the pyramid.
     */
    private final float height;
    /**
     * The width of the pyramid.
     */
    private final float width;
    /**
     * The optional depth of the pyramid. Defaults to the width if unset.
     */
    @Nullable
    private final Float depth;

    public PrimitivePyramid(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                            @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                            @Nullable Float maximumRenderDistance, float height, float width,
                            @Nullable Float depth, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, maximumRenderDistance, attachedToEntityId);
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    @Override
    public Type getType() {
        return Type.PYRAMID;
    }
}
