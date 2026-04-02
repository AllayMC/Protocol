package org.cloudburstmc.protocol.bedrock.data.debugshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents debug box used in the Bedrock protocol.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class DebugBox extends DebugShape {

    /**
     * The box bounds.
     */
    private final Vector3f boxBounds;

    public DebugBox(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                    @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                    Vector3f boxBounds) {
        this(id, dimension, position, scale, rotation, totalTimeLeft, color, boxBounds, null);
    }

    public DebugBox(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                    @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                    Vector3f boxBounds, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, attachedToEntityId);
        this.boxBounds = boxBounds;
    }

    @Override
    public Type getType() {
        return Type.BOX;
    }
}
