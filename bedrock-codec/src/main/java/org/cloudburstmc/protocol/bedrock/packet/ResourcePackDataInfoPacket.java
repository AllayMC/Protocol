package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ResourcePackType;
import org.cloudburstmc.protocol.common.PacketSignal;
import java.util.UUID;

/**
 * Sent by the server to the client to inform the client about the data contained in one of the
 * resource packs that are about to be sent.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePackDataInfoPacket implements BedrockPacket {
    private UUID packId;
    private String packVersion;
    private long maxChunkSize;
    private long chunkCount;
    private long compressedPackSize;
    private byte[] hash;
    /**
     * @since v361
     */
    private boolean premium;
    /**
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
