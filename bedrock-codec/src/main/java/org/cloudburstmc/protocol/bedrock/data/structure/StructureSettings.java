package org.cloudburstmc.protocol.bedrock.data.structure;

import lombok.Value;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

@Value
public class StructureSettings {
    private final boolean ignoringEntities;
    private final boolean ignoringBlocks;
    private final Vector3i size;
    private final Vector3i offset;
    private final StructureRotation rotation;
    private final StructureMirror mirror;
    private final float integrityValue;
    private final int integritySeed;
    /**
     * @since v361
     */
    private final String paletteName;
    /**
     * @since v361
     */
    private final long lastEditedByEntityId;
    /**
     * @since v388
     */
    private final Vector3f pivot;
    /**
     * @since v440
     */
    private final StructureAnimationMode animationMode;
    /**
     * @since v440
     */
    private final float animationSeconds;
    /**
     * @since v503
     */
    private final boolean nonTickingPlayersAndTickingAreasEnabled;
}
