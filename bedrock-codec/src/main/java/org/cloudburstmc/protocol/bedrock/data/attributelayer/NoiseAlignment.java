package org.cloudburstmc.protocol.bedrock.data.attributelayer;


/**
 * Aligns an environment attribute's noise transition with a local transition.
 *
 * @param type  the alignment mode
 * @param value the alignment value interpreted by that mode
 * @since v2192
 */
public record NoiseAlignment(Type type, int value) {

    /**
     * Noise transition alignment modes.
     *
     * @since v2192
     */
    public enum Type {
        MIN_LOCAL_TRANSITION_END
    }
}
