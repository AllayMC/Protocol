package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;

/**
 * An environment attribute and its transition state.
 * Since v2225 the wire selects a constant, transition, or noise-transition variant.
 * A constant uses {@code attribute}; transitions use {@code from} and {@code to}.
 *
 * @param attributeName          the attribute identifier
 * @param from                   the optional starting value; required for transition variants since v2225
 * @param attribute              the current value; used only by the constant variant since v2225
 * @param to                     the optional target value; required for transition variants since v2225
 * @param CurrentTransitionTicks the elapsed transition ticks; omitted for constants since v2225
 * @param TotalTransitionTicks   the total transition ticks; omitted for constants since v2225
 * @param easing                 the easing function; omitted for constants since v2225
 * @param clockName              the clock used by a transition, since v2225
 * @param localTransitionTicks   the local transition tick count, since v1001; noise transitions only since v2225
 * @param noiseTransition        whether the attribute uses a noise transition, since v1001
 * @param noiseName              the noise identifier for a noise transition, since v2225
 * @param noiseAlignment         the noise alignment, since v2192; noise transitions only since v2225
 * @since v944
 */
public record EnvironmentAttributeData(String attributeName, @Nullable AttributeData from, AttributeData attribute,
                                       @Nullable AttributeData to, int CurrentTransitionTicks,
                                       int TotalTransitionTicks, CameraEase easing, String clockName,
                                       int localTransitionTicks, boolean noiseTransition, String noiseName,
                                       NoiseAlignment noiseAlignment) {

    public EnvironmentAttributeData(String attributeName, AttributeData from, AttributeData attribute, AttributeData to,
                                    int CurrentTransitionTicks, int TotalTransitionTicks, CameraEase easing,
                                    int localTransitionTicks, boolean noiseTransition, NoiseAlignment noiseAlignment) {
        this(attributeName, from, attribute, to, CurrentTransitionTicks, TotalTransitionTicks, easing,
                null, localTransitionTicks, noiseTransition, null, noiseAlignment);
    }

    public EnvironmentAttributeData(String attributeName, AttributeData from, AttributeData attribute, AttributeData to,
                                    int CurrentTransitionTicks, int TotalTransitionTicks, CameraEase easing,
                                    int localTransitionTicks, boolean noiseTransition) {
        this(attributeName, from, attribute, to, CurrentTransitionTicks, TotalTransitionTicks, easing, localTransitionTicks, noiseTransition, null);
    }

    public EnvironmentAttributeData(String attributeName, @Nullable AttributeData from, AttributeData attribute,
                                    @Nullable AttributeData to, int CurrentTransitionTicks,
                                    int TotalTransitionTicks, CameraEase easing) {
        this(attributeName, from, attribute, to, CurrentTransitionTicks, TotalTransitionTicks, easing, 0, false);
    }
}
