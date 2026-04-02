package org.cloudburstmc.protocol.bedrock.data.skin;

/**
 * Represents animation data used in the Bedrock protocol.
 *
 * @param image          The image.
 * @param textureType    The texture type.
 * @param frames         The frames.
 * @param expressionType The expression type.
 */
public record AnimationData(ImageData image, AnimatedTextureType textureType, float frames, AnimationExpressionType expressionType) {
    public AnimationData(ImageData image, AnimatedTextureType textureType, float frames) {
        this(image, textureType, frames, AnimationExpressionType.LINEAR);
    }
}
