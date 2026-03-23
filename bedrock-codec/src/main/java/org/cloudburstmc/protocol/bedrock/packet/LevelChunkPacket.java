package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to provide the client with a chunk of a world data (16xYx16 blocks).
 * Typically, a certain amount of chunks is sent to the client before sending it the spawn
 * PlayStatus packet, so that the client spawns in a loaded world.
 */
@Data
@ToString(
        doNotUseGetters = true,
        exclude = {"data"})
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class LevelChunkPacket extends AbstractReferenceCounted implements BedrockPacket {
    private int chunkX;
    private int chunkZ;

    /**
     * @since v361
     */
    private int subChunksLength;

    /**
     * @since v361
     */
    private boolean cachingEnabled;

    /**
     * @since v486
     */
    private boolean requestSubChunks;

    /**
     * @since v486
     */
    private int subChunkLimit;

    /**
     * @since v361
     */
    private final LongList blobIds = new LongArrayList();

    private ByteBuf data;

    /**
     * @since v649
     */
    private int dimension;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_CHUNK;
    }

    @Override
    public LevelChunkPacket touch(Object hint) {
        this.data.touch(hint);
        return this;
    }

    @Override
    protected void deallocate() {
        this.data.release();
    }

    @Override
    public LevelChunkPacket clone() {
        throw new UnsupportedOperationException("Can not clone reference counted packet");
    }
}
