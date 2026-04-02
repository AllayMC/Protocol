package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ResourcePackType;

import java.util.UUID;

/**
 * Sent by the server to the client to inform the client about the data contained in one of the
 * resource packs that are about to be sent.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePackDataInfoPacket implements BedrockPacket {
    /**
     * The UUID of the resource pack described by this packet.
     */
    private UUID packId;
    /**
     * The version string of the resource pack described by this packet.
     */
    private String packVersion;
    /**
     * DataChunkSize is the maximum size in bytes of the chunks in which the total size of the
     * resource pack to be sent will be divided. A size of 1MB (1024*1024) means that a resource
     * pack of 15.5MB will be split into 16 data chunks.
     */
    private long maxChunkSize;
    /**
     * The total number of compressed chunks that make up the pack archive.
     */
    private long chunkCount;
    /**
     * The total size in bytes of the compressed pack archive.
     */
    private long compressedPackSize;
    /**
     * A SHA256 hash of the content of the resource pack.
     */
    private byte[] hash;
    /**
     * Specifies if the resource pack was a premium resource pack, meaning it was bought from the
     * Minecraft store.
     *
     * @since v361
     */
    private boolean premium;
    /**
     * The type of pack being transferred.
     *
     * @since v361
     */
    private ResourcePackType type;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_DATA_INFO;
    }

    @Override
    public ResourcePackDataInfoPacket clone() {
        try {
            return (ResourcePackDataInfoPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
