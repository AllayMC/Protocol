package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CameraInstructionSet represents a camera instruction that sets the camera to a specified preset
 * and can be extended with easing functions and translations to the camera's position and rotation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraFovInstruction {
    /**
     * The fov.
     */
    private float fov;
    /**
     * The ease time.
     */
    private float easeTime;
    /**
     * The ease type.
     */
    private CameraEase easeType;
    /**
     * Whether clear.
     */
    private boolean clear;
}
