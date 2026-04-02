package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.ControlScheme;
import org.cloudburstmc.protocol.bedrock.util.OptionalBoolean;

/**
 * Represents a basic preset that can be extended upon by more complex instructions.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CameraPreset {
    /**
     * The identifier.
     */
    private String identifier;
    /**
     * The parent preset.
     */
    @Builder.Default
    private String parentPreset = "";
    // All the values below are optional, and will not be encoded if null is used
    /**
     * The pos.
     */
    private Vector3f pos;
    /**
     * The yaw.
     */
    private Float yaw;
    /**
     * The pitch.
     */
    private Float pitch;
    /**
     * The listener.
     *
     * @since v618
     */
    private CameraAudioListener listener;
    /**
     * The play effect.
     *
     * @since v618
     */
    @Builder.Default
    private OptionalBoolean playEffect = OptionalBoolean.empty();
    /**
     * The only used in a follow_orbit camera and controls an offset based on a pivot point to the
     * player, causing it to be shifted in a certain direction.
     *
     * @since v712
     */
    private Vector2f viewOffset;
    /**
     * The only used in a follow_orbit camera and controls how far away from the player the camera
     * should be rendered.
     *
     * @since v712
     */
    private Float radius;
    /**
     * The speed at which the camera should rotate.
     *
     * @since v729
     */
    private Float rotationSpeed;
    /**
     * SnapToTarget determines whether the camera should snap to the target entity or not.
     *
     * @since v729
     */
    @Builder.Default
    private OptionalBoolean snapToTarget = OptionalBoolean.empty();
    /**
     * EntityOffset controls the offset from the entity that the camera should be rendered at.
     *
     * @since v729
     */
    private Vector3f entityOffset;
    /**
     * The horizontal rotation limit of the camera.
     *
     * @since v748
     */
    private Vector2f horizontalRotationLimit;
    /**
     * The vertical rotation limit of the camera.
     *
     * @since v748
     */
    private Vector2f verticalRotationLimit;
    /**
     * ContinueTargeting determines whether the camera should continue targeting when using aim
     * assist.
     *
     * @since v748
     */
    @Builder.Default
    private OptionalBoolean continueTargeting = OptionalBoolean.empty();
    /**
     * The align target and camera forward.
     *
     * @since v748
     * @deprecated v818
     */
    @Deprecated
    @Builder.Default
    private OptionalBoolean alignTargetAndCameraForward = OptionalBoolean.empty();
    /**
     * The block listening radius.
     *
     * @since v766
     */
    private Float blockListeningRadius;
    /**
     * AimAssist defines the aim assist to use when using this preset.
     *
     * @since v766
     */
    private CameraAimAssistPreset aimAssistPreset;
    /**
     * The minimum yaw limit of the camera.
     *
     * @since v776
     */
    private Float minYawLimit;
    /**
     * The maximum yaw limit of the camera.
     *
     * @since v776
     */
    private Float maxYawLimit;
    /**
     * The control scheme that the client should use in this camera. It is one of the following: -
     * ControlSchemeLockedPlayerRelativeStrafe is the default behaviour, this cannot be set when the
     * client is in a custom camera. - ControlSchemeCameraRelative makes movement relative to the
     * camera's transform, with the client's rotation being relative to the client's movement. -
     * ControlSchemeCameraRelativeStrafe makes movement relative to the camera's transform, with the
     * client's rotation being locked. - ControlSchemePlayerRelative makes movement relative to the
     * player's transform, meaning holding left/right will make the player turn in a circle. -
     * ControlSchemePlayerRelativeStrafe makes movement the same as the default behaviour, but can
     * be used in a custom camera.
     *
     * @since v800
     */
    @Nullable
    private ControlScheme controlScheme;
}
