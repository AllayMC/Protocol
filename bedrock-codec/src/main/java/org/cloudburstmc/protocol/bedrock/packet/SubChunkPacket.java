package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.util.AbstractReferenceCounted;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.SubChunkData;

import java.util.List;

/**
 * Sent by the server to return multiple sub-chunks relative to a shared center position.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class SubChunkPacket extends AbstractReferenceCounted implements BedrockPacket {
    /**
     * The dimension containing the returned sub-chunks.
     */
    private int dimension;
    /**
     * The sub-chunk entries included in the response.
     */
    private List<SubChunkData> subChunks = new ObjectArrayList<>();
    /**
     * Whether blob caching is enabled for the returned entries.
     *
     * @since v475
     */
    private boolean cacheEnabled;
    /**
     * The absolute sub-chunk center position used as the base for the entries in {@link #subChunks}.
     *
     * @since v486
     */
    private Vector3i centerPosition;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SUB_CHUNK;
    }

    @Override
    public SubChunkPacket touch(Object o) {
        this.subChunks.forEach(SubChunkData::touch);
        return this;
    }

    @Override
    protected void deallocate() {
        this.subChunks.forEach(SubChunkData::release);
    }

    @Override
    public SubChunkPacket clone() {
        throw new UnsupportedOperationException("Can not clone reference counted packet");
    }
}
