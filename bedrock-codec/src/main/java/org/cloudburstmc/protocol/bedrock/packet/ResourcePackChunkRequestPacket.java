package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.UUID;

/**
 * Sent by the client to request a chunk of data from a particular resource pack, that it has
 * obtained information about in a ResourcePackDataInfo packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePackChunkRequestPacket implements BedrockPacket {
    /**
     * The UUID of the resource pack being downloaded.
     */
    private UUID packId;
    /**
     * The version string of the resource pack being downloaded.
     */
    private String packVersion;
    /**
     * The zero-based index of the compressed data chunk being requested.
     */
    private int chunkIndex;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_CHUNK_REQUEST;
    }

    @Override
    public ResourcePackChunkRequestPacket clone() {
        try {
            return (ResourcePackChunkRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
