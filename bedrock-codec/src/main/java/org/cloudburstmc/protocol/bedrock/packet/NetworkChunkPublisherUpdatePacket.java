package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2i;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to change the point around which chunks are and remain loaded. This is useful
 * for mini-game servers, where only one area is ever loaded, in which case the
 * NetworkChunkPublisherUpdate packet can be sent in the middle of it, so that no chunks ever need
 * to be additionally sent during the course of the game. In reality, the packet is not
 * extraordinarily useful, and most servers just send it constantly at the position of the player.
 * If the packet is not sent at all, no chunks will be shown to the player, regardless of where they
 * are sent.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class NetworkChunkPublisherUpdatePacket implements BedrockPacket {
    /**
     * The block position around which chunks loaded will remain shown to the client. Most servers
     * set this position to the position of the player itself.
     */
    private Vector3i position;
    /**
     * The radius in blocks around Position that chunks sent show up in and will remain loaded in.
     * Unlike the RequestChunkRadius and ChunkRadiusUpdated packets, this radius is in blocks rather
     * than chunks, so the chunk radius needs to be multiplied by 16. (Or shifted to the left by
     * 4.).
     */
    private int radius;
    /**
     * Lists chunk coordinates that the client should request again when client-side chunk generation
     * is enabled.
     *
     * @since v544
     */
    private final List<Vector2i> savedChunks = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NETWORK_CHUNK_PUBLISHER_UPDATE;
    }

    @Override
    public NetworkChunkPublisherUpdatePacket clone() {
        try {
            return (NetworkChunkPublisherUpdatePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
