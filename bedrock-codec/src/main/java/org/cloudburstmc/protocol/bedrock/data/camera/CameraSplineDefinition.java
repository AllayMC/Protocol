package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a named camera spline definition.
 */
@Data
@AllArgsConstructor
public class CameraSplineDefinition {

    /**
     * The name of the spline definition.
     */
    private String name;
    /**
     * The instruction.
     */
    private CameraSplineInstruction instruction;
}
