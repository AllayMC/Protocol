package org.cloudburstmc.protocol.bedrock.data.attributelayer;

/**
 * Represents settings for an attribute layer.
 *
 * @param priority          The priority of the layer.
 * @param weight            The weight.
 * @param enabled           Enabled indicates if the layer is enabled.
 * @param transitionsPaused TransitionsPaused indicates if transitions are paused for this layer.
 */
public record AttributeLayerSettings(int priority, Weight weight, boolean enabled, boolean transitionsPaused) {

    public interface Weight {
    }

    public record FloatWeight(float value) implements Weight {
    }

    public record StringWeight(String value) implements Weight {
    }
}
