package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to provide telemetry timing samples for networking and server processing.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class ServerStatsPacket implements BedrockPacket {
    /**
     * The measured time spent on server-side processing.
     */
    private float serverTime;
    /**
     * The measured time attributed to network processing.
     */
    private float networkTime;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVER_STATS;
    }

    @Override
    public ServerStatsPacket clone() {
        try {
            return (ServerStatsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
