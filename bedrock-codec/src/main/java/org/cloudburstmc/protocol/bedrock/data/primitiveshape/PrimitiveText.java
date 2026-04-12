package org.cloudburstmc.protocol.bedrock.data.primitiveshape;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents primitive text used in the Bedrock protocol.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PrimitiveText extends PrimitiveShape {

    /**
     * The text.
     */
    private final String text;
    /**
     * Whether the text uses the shape rotation.
     *
     * @since v974
     */
    private final boolean useRotation;
    /**
     * The optional background colour of the text.
     *
     * @since v974
     */
    @Nullable
    private final Color backgroundColor;
    /**
     * Whether the text participates in depth testing.
     *
     * @since v974
     */
    private final boolean depthTest;
    /**
     * Whether the text background renders its backface.
     *
     * @since v974
     */
    private final boolean showBackface;
    /**
     * Whether the text renders its backface.
     *
     * @since v974
     */
    private final boolean showTextBackface;

    public PrimitiveText(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                         @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                         String text) {
        this(id, dimension, position, scale, rotation, totalTimeLeft, color, text, false, null, false, false, false, null, null);
    }

    public PrimitiveText(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                         @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                         String text, @Nullable Long attachedToEntityId) {
        this(id, dimension, position, scale, rotation, totalTimeLeft, color, text, false, null, false, false, false, null, attachedToEntityId);
    }

    public PrimitiveText(long id, int dimension, @Nullable Vector3f position, @Nullable Float scale,
                         @Nullable Vector3f rotation, @Nullable Float totalTimeLeft, @Nullable Color color,
                         String text, boolean useRotation, @Nullable Color backgroundColor, boolean depthTest,
                         boolean showBackface, boolean showTextBackface, @Nullable Float maximumRenderDistance,
                         @Nullable Long attachedToEntityId) {
        super(id, dimension, position, scale, rotation, totalTimeLeft, color, maximumRenderDistance, attachedToEntityId);
        this.text = text;
        this.useRotation = useRotation;
        this.backgroundColor = backgroundColor;
        this.depthTest = depthTest;
        this.showBackface = showBackface;
        this.showTextBackface = showTextBackface;
    }

    @Override
    public Type getType() {
        return Type.TEXT;
    }
}
