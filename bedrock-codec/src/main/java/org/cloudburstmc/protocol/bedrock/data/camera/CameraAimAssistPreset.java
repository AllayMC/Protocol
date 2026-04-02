package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.cloudburstmc.math.vector.Vector2f;

/**
 * CameraAimAssistPreset defines a base preset that can be extended upon when sending an aim assist.
 */
@Data
@Builder
@AllArgsConstructor
public class CameraAimAssistPreset {
    /**
     * Identifier represents the identifier of this preset.
     */
    private String identifier;
    /**
     * The target mode.
     */
    private Integer targetMode;
    /**
     * The angle.
     */
    private Vector2f angle;
    /**
     * The distance.
     */
    private Float distance;
}
