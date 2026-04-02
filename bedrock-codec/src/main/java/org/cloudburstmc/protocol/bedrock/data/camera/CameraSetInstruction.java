package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.definition.NamedDefinition;
import org.cloudburstmc.protocol.bedrock.util.OptionalBoolean;

/**
 * CameraInstructionSet represents a camera instruction that sets the camera to a specified preset
 * and can be extended with easing functions and translations to the camera's position and rotation.
 */
@Data
@NoArgsConstructor
public class CameraSetInstruction {
    /**
     * The index of the preset in the CameraPresets packet sent to the player.
     */
    private NamedDefinition preset;
    /**
     * Represents the easing function that is used by the instruction.
     */
    private EaseData ease;
    /**
     * The pos.
     */
    private Vector3f pos;
    /**
     * The rot.
     */
    private Vector2f rot;
    /**
     * The default preset.
     */
    private OptionalBoolean defaultPreset = OptionalBoolean.empty();
    /**
     * A vector that the camera will always face towards during the duration of the instruction.
     *
     * @since v618
     */
    private Vector3f facing;
    /**
     * An offset based on a pivot point to the player, causing the camera to be shifted in a certain
     * direction.
     *
     * @since v712
     */
    private Vector2f viewOffset;
    /**
     * An offset from the entity that the camera should be rendered at.
     *
     * @since v748
     */
    private Vector3f entityOffset;
    /**
     * IgnoreStartingValuesComponent behavior is currently unknown.
     *
     * @since v818
     */
    private boolean removeIgnoreStartingValues;

    @Builder
    public CameraSetInstruction(NamedDefinition preset, EaseData ease, Vector3f pos, Vector2f rot,
                                Vector3f facing, Vector2f viewOffset, Vector3f entityOffset,
                                OptionalBoolean defaultPreset, boolean removeIgnoreStartingValues) {
        this.preset = preset;
        this.ease = ease;
        this.pos = pos;
        this.rot = rot;
        this.facing = facing;
        this.viewOffset = viewOffset;
        this.entityOffset = entityOffset;
        this.defaultPreset = defaultPreset != null ? defaultPreset : OptionalBoolean.empty();
        this.removeIgnoreStartingValues = removeIgnoreStartingValues;
    }

    public record EaseData(CameraEase easeType, float time) {
    }
}
