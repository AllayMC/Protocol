package org.cloudburstmc.protocol.bedrock.data.debugshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents debug text used in the Bedrock protocol.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class DebugText extends DebugShape {

    /**
     * The text.
     */
    private final String text;

    public DebugText(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                     @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                     String text) {
        this(id, dimension, position, scale, rotation, totalTimeLeft, color, text, null);
    }

    public DebugText(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                     @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                     String text, @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, attachedToEntityId);
        this.text = text;
    }

    @Override
    public Type getType() {
        return Type.TEXT;
    }
}
