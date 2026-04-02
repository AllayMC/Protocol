package org.cloudburstmc.protocol.bedrock.data.structure;

import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;

/**
 * Represents a struct holding settings of a structure block. Its fields may be changed using the
 * in-game UI on the client-side.
 *
 * @param ignoringEntities                        Whether ignoring entities.
 * @param ignoringBlocks                          Whether ignoring blocks.
 * @param size                                    The size of the area that is about to be exported. The area exported will start at the
 *                                                Position + Offset, and will extend as far as Size specifies.
 * @param offset                                  The offset position that was set in the structure block. The area exported is offset by this
 *                                                position.
 * @param rotation                                The rotation that the structure block should apply to the exported or loaded structure.
 * @param mirror                                  Specifies the way the structure should be mirrored. It is either no mirror at all, mirror on
 *                                                the x/z axis or both.
 * @param integrityValue                          The integrity value.
 * @param integritySeed                           The integrity seed.
 * @param paletteName                             The name of the palette used in the structure. Currently, it seems that this field is always
 *                                                'default'.
 * @param lastEditedByEntityId                    The last edited by entity ID.
 * @param pivot                                   The pivot around which the structure may be rotated.
 * @param animationMode                           The animation mode.
 * @param animationSeconds                        The animation seconds.
 * @param nonTickingPlayersAndTickingAreasEnabled Whether non ticking players and ticking areas enabled.
 */
public record StructureSettings(boolean ignoringEntities, boolean ignoringBlocks, Vector3i size, Vector3i offset, StructureRotation rotation,
                                StructureMirror mirror, float integrityValue, int integritySeed, String paletteName, long lastEditedByEntityId,
                                Vector3f pivot, StructureAnimationMode animationMode, float animationSeconds,
                                boolean nonTickingPlayersAndTickingAreasEnabled) {
}
