package org.cloudburstmc.protocol.bedrock.data.skin;

/**
 * Enumerates animation expression type values used in the Bedrock protocol.
 */
public enum AnimationExpressionType {
    LINEAR,
    BLINKING;

    private static final AnimationExpressionType[] VALUES = values();

    public static AnimationExpressionType from(int id) {
        return VALUES[id];
    }
}
