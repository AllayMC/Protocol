package org.cloudburstmc.protocol.bedrock.data.structure;

import lombok.Value;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

@Value
public class StructureSettings {
    /**
     * @since v361
     */
    private final String paletteName;
    private final boolean ignoringEntities;
    private final boolean ignoringBlocks;
    /**
     * @since v503
     */
    private final boolean nonTickingPlayersAndTickingAreasEnabled;
    private final Vector3i size;
    private final Vector3i offset;
    /**
     * @since v361
     */
    private final long lastEditedByEntityId;
    private final StructureRotation rotation;
    private final StructureMirror mirror;
    /**
     * @since v440
     */
    private final StructureAnimationMode animationMode;
    /**
     * @since v440
     */
    private final float animationSeconds;
    private final float integrityValue;
    private final int integritySeed;
    /**
     * @since v388
     */
    private final Vector3f pivot;
}
