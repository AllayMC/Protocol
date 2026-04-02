package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.annotation.Incompressible;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;

/**
 * Sent by the server to update a variety of network settings. These settings modify the way packets
 * are sent over the network stack.
 */
@Data
@Incompressible
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class NetworkSettingsPacket implements BedrockPacket {
    /**
     * The minimum packet size, in bytes, that should be compressed before it is sent. Packets
     * smaller than this threshold are left uncompressed, and a value of {@code 0} disables packet
     * compression entirely.
     */
    private int compressionThreshold;
    /**
     * The compression algorithm used for packets on this connection.
     *
     * @since v554
     */
    private PacketCompressionAlgorithm compressionAlgorithm;
    /**
     * Specifies if the client should throttle remote players once the player count exceeds
     * {@link #clientThrottleThreshold}. Players outside the retained set are not ticked client-side,
     * which reduces the performance hit on lower-end devices in crowded areas.
     *
     * @since v554
     */
    private boolean clientThrottleEnabled;
    /**
     * The threshold for client throttling. If the number of players exceeds this value, the client
     * will throttle players.
     *
     * @since v554
     */
    private int clientThrottleThreshold;
    /**
     * The scalar for client throttling. The scalar is the amount of players that are ticked when
     * throttling is enabled.
     *
     * @since v554
     */
    private float clientThrottleScalar;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NETWORK_SETTINGS;
    }

    @Override
    public NetworkSettingsPacket clone() {
        try {
            return (NetworkSettingsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
