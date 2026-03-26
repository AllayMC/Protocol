package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.ControlScheme;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CameraPreset {
    private String identifier;
    @Builder.Default
    private String parentPreset = "";
    // All the values below are optional, and will not be encoded if null is used
    private Vector3f pos;
    private Float yaw;
    private Float pitch;
    /**
     * @since v618
     */
    private CameraAudioListener listener;
    /**
     * @since v618
     */
    @Builder.Default
    private OptionalBoolean playEffect = OptionalBoolean.empty();
    /**
     * @since v712
     */
    private Vector2f viewOffset;
    /**
     * @since v712
     */
    private Float radius;
    /**
     * @since v729
     */
    private Float rotationSpeed;
    /**
     * @since v729
     */
    @Builder.Default
    private OptionalBoolean snapToTarget = OptionalBoolean.empty();
    /**
     * @since v729
     */
    private Vector3f entityOffset;
    /**
     * @since v748
     */
    private Vector2f horizontalRotationLimit;
    /**
     * @since v748
     */
    private Vector2f verticalRotationLimit;
    /**
     * @since v748
     */
    @Builder.Default
    private OptionalBoolean continueTargeting = OptionalBoolean.empty();
    /**
     * @since v748
     * @deprecated v818
     */
    @Builder.Default
    private OptionalBoolean alignTargetAndCameraForward = OptionalBoolean.empty();
    /**
     * @since v766
     */
    private Float blockListeningRadius;
    /**
     * @since v766
     */
    private CameraAimAssistPreset aimAssistPreset;
    /**
     * @since v776
     */
    private Float minYawLimit;
    /**
     * @since v776
     */
    private Float maxYawLimit;
    /**
     * @since v800
     */
    @Nullable
    private ControlScheme controlScheme;

    public CameraPreset(String identifier, String parentPreset, Vector3f pos, Float yaw, Float pitch,
                        Vector2f viewOffset, Float radius, Float minYawLimit, Float maxYawLimit,
                        CameraAudioListener listener, OptionalBoolean playEffect, Float rotationSpeed,
                        OptionalBoolean snapToTarget, Vector3f entityOffset, Vector2f horizontalRotationLimit,
                        Vector2f verticalRotationLimit, OptionalBoolean continueTargeting,
                        OptionalBoolean alignTargetAndCameraForward, Float blockListeningRadius,
                        CameraAimAssistPreset aimAssistPreset, @Nullable ControlScheme controlScheme) {
        this.identifier = identifier;
        this.parentPreset = parentPreset;
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
        this.listener = listener;
        this.playEffect = playEffect;
        this.viewOffset = viewOffset;
        this.radius = radius;
        this.rotationSpeed = rotationSpeed;
        this.snapToTarget = snapToTarget;
        this.entityOffset = entityOffset;
        this.horizontalRotationLimit = horizontalRotationLimit;
        this.verticalRotationLimit = verticalRotationLimit;
        this.continueTargeting = continueTargeting;
        this.alignTargetAndCameraForward = alignTargetAndCameraForward;
        this.blockListeningRadius = blockListeningRadius;
        this.aimAssistPreset = aimAssistPreset;
        this.minYawLimit = minYawLimit;
        this.maxYawLimit = maxYawLimit;
        this.controlScheme = controlScheme;
    }
}
