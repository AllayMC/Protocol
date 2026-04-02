package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.UUID;

/**
 * Sent to the client so that the client can download the resource pack. Each packet holds a chunk
 * of the compressed resource pack, of which the size is defined in the ResourcePackDataInfo packet
 * sent before.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(
        doNotUseGetters = true,
        exclude = {"data"})
public class ResourcePackChunkDataPacket extends AbstractReferenceCounted implements BedrockPacket {
    /**
     * The pack ID.
     */
    private UUID packId;
    /**
     * The pack version.
     */
    private String packVersion;
    /**
     * The current chunk index of the chunk. It is a number that starts at 0 and is incremented for
     * each resource pack data chunk sent to the client.
     */
    private int chunkIndex;
    /**
     * The progress.
     */
    private long progress;
    /**
     * RawPayload is a byte slice containing a chunk of data from the resource pack. It must be of
     * the same size or less than the DataChunkSize set in the ResourcePackDataInfo packet.
     */
    private ByteBuf data;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_CHUNK_DATA;
    }

    @Override
    protected void deallocate() {
        this.data.release();
    }

    @Override
    public ResourcePackChunkDataPacket touch(Object hint) {
        data.touch(hint);
        return this;
    }

    @Override
    public ResourcePackChunkDataPacket clone() {
        throw new UnsupportedOperationException("Can not clone reference counted packet");
    }
}
