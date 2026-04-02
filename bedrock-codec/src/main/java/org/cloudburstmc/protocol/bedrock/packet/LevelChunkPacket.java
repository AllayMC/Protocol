package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
    /**
     * The chunk X coordinate. A block coordinate may be converted to a chunk coordinate by shifting
     * it right by four bits.
     */
    private int chunkX;
    /**
     * The chunk Z coordinate.
     */
    private int chunkZ;
    /**
     * The serialized chunk payload, including sub-chunk data and block entities.
     */
    private ByteBuf data;
    /**
     * The number of sub-chunks described by this payload. Special values may be used to make the
     * client request sub-chunks separately.
     *
     * @since v361
     */
    private int subChunksLength;
    /**
     * Whether the blob cache is enabled for this chunk payload.
     *
     * @since v361
     */
    private boolean cachingEnabled;
    /**
     * The blob hashes referenced by this chunk when client blob caching is enabled. These are
     * usually the hashes of each sub-chunk plus the biome blob.
     *
     * @since v361
     */
    private final LongList blobIds = new LongArrayList();
    /**
     * Whether the client should request missing sub-chunks separately.
     *
     * @since v486
     */
    private boolean requestSubChunks;
    /**
     * The highest non-air sub-chunk index, used when {@link #requestSubChunks} is set in limited
     * sub-chunk request mode.
     *
     * @since v486
     */
    private int subChunkLimit;
    /**
     * The ID of the dimension that the chunk belongs to. This must always be set otherwise the
     * client will always assume the chunk is part of the overworld dimension.
     *
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
