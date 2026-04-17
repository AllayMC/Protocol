package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.BlockChangeEntry;

import java.util.List;

/**
 * A batched variant of {@link UpdateBlockPacket} that updates multiple blocks within the same
 * sub-chunk.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateSubChunkBlocksPacket implements BedrockPacket {
    /**
     * The block position of the sub-chunk.
     */
    private Vector3i position;

    /**
     * Block changes for the default block layer.
     */
    private final List<BlockChangeEntry> standardBlocks = new ObjectArrayList<>();
    /**
     * Block changes for the extra block layer, usually used for waterlogged or similar blocks.
     */
    private final List<BlockChangeEntry> extraBlocks = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_SUB_CHUNK_BLOCKS;
    }

    @Override
    public UpdateSubChunkBlocksPacket clone() {
        try {
            return (UpdateSubChunkBlocksPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
