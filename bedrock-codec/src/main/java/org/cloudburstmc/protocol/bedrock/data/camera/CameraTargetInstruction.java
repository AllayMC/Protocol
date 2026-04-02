package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cloudburstmc.math.vector.Vector3f;

/**
 * CameraInstructionTarget represents a camera instruction that targets a specific entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraTargetInstruction {
    /**
     * CenterOffset is the offset from the center of the entity that the camera should target.
     */
    private Vector3f targetCenterOffset;
    /**
     * The unique ID of the entity that the camera should target.
     */
    private long uniqueEntityId;
}
