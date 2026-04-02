package org.cloudburstmc.protocol.bedrock.data;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.math.vector.Vector3i;

/**
 * Holds one sub-chunk entry contained in a {@code SubChunkPacket}.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SubChunkData extends AbstractReferenceCounted {
    /**
     * The absolute sub-chunk position of this entry.
     */
    private Vector3i position;
    /**
     * The serialized sub-chunk data buffer.
     */
    private ByteBuf data;
    /**
     * The result code for this requested sub-chunk.
     */
    private SubChunkRequestResult result;
    /**
     * The height map encoding type.
     */
    private HeightMapDataType heightMapType;
    /**
     * The serialized height map buffer.
     */
    private ByteBuf heightMapData;
    /**
     * Whether this entry uses blob caching.
     */
    private boolean cacheEnabled;
    /**
     * The cached blob ID, used when {@link #cacheEnabled} is true.
     *
     * @since v475
     */
    private long blobId;
    /**
     * The render height map encoding type.
     *
     * @since v818
     */
    private HeightMapDataType renderHeightMapType;
    /**
     * The serialized render height map buffer.
     *
     * @since v818
     */
    private ByteBuf renderHeightMapData;

    @Override
    public SubChunkData touch(Object o) {
        if (this.data != null) {
            this.data.touch(o);
        }
        if (this.heightMapData != null) {
            this.heightMapData.touch(o);
        }
        return this;
    }

    @Override
    protected void deallocate() {
        if (this.data != null) {
            this.data.release();
        }
        if (this.heightMapData != null) {
            this.heightMapData.release();
        }
    }
}
