package org.cloudburstmc.protocol.bedrock.data;

import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

/**
 * Represents a single block change entry in
 * {@link org.cloudburstmc.protocol.bedrock.packet.UpdateSubChunkBlocksPacket}.
 *
 * @param position        The block position relative to the sub-chunk being updated.
 * @param definition      The block definition that should be written at that position.
 * @param updateFlags     The block update flags controlling how the client should apply the change.
 * @param messageEntityId The entity runtime ID associated with the change message, when applicable.
 * @param messageType     The type of block change message to emit for this entry.
 */
public record BlockChangeEntry(Vector3i position, BlockDefinition definition, int updateFlags, long messageEntityId, MessageType messageType) {
    public enum MessageType {
        NONE,
        CREATE,
        DESTROY
    }
}
