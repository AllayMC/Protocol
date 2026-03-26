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
    private final Vector3i size;
    private final Vector3i offset;
    private final StructureRotation rotation;
    private final StructureMirror mirror;
    private final float integrityValue;
    private final int integritySeed;

    public StructureSettings(String paletteName, boolean ignoringEntities, boolean ignoringBlocks,
                             boolean nonTickingPlayersAndTickingAreasEnabled, Vector3i size, Vector3i offset,
                             long lastEditedByEntityId, StructureRotation rotation, StructureMirror mirror,
                             StructureAnimationMode animationMode, float animationSeconds, float integrityValue,
                             int integritySeed, Vector3f pivot) {
        this.paletteName = paletteName;
        this.ignoringEntities = ignoringEntities;
        this.ignoringBlocks = ignoringBlocks;
        this.lastEditedByEntityId = lastEditedByEntityId;
        this.pivot = pivot;
        this.animationMode = animationMode;
        this.animationSeconds = animationSeconds;
        this.nonTickingPlayersAndTickingAreasEnabled = nonTickingPlayersAndTickingAreasEnabled;
        this.size = size;
        this.offset = offset;
        this.rotation = rotation;
        this.mirror = mirror;
        this.integrityValue = integrityValue;
        this.integritySeed = integritySeed;
    }
}
