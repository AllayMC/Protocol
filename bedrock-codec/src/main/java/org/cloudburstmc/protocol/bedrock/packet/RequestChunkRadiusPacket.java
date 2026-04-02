package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the client to the server to update the server on the chunk view radius that it has set in
 * the settings. The server may respond with a ChunkRadiusUpdated packet with either the chunk
 * radius requested, or a different chunk radius if the server chooses so.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RequestChunkRadiusPacket implements BedrockPacket {
    /**
     * The chunk radius currently requested in the client's video settings.
     */
    private int radius;
    /**
     * The maximum chunk radius that the client is prepared to receive.
     *
     * @since v582
     */
    private int maxRadius;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REQUEST_CHUNK_RADIUS;
    }

    @Override
    public RequestChunkRadiusPacket clone() {
        try {
            return (RequestChunkRadiusPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
