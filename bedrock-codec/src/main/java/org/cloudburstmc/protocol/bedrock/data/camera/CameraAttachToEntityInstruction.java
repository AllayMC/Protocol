package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents camera attach to entity instruction used in the Bedrock protocol.
 */
@Data
@AllArgsConstructor
public class CameraAttachToEntityInstruction {

    /**
     * The unique entity ID.
     */
    private long uniqueEntityId;
}
