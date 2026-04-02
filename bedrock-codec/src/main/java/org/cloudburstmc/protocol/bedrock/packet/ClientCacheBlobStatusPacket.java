package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Part of the blob cache protocol. It is sent by the client to let the server know what blobs it
 * needs and which blobs it already has, in an ACK type system.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientCacheBlobStatusPacket implements BedrockPacket {
    /**
     * Blob hashes that the client already has available locally, so the server does not need to
     * resend them.
     */
    private final LongList acks = new LongArrayList();
    /**
     * Blob hashes that the client is missing and wants the server to send as soon as possible.
     */
    private final LongList naks = new LongArrayList();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENT_CACHE_BLOB_STATUS;
    }

    @Override
    public ClientCacheBlobStatusPacket clone() {
        try {
            return (ClientCacheBlobStatusPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
