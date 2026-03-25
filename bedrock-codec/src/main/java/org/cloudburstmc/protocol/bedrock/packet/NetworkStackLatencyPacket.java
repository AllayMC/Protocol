package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server (and the client, on development builds) to measure the latency over the entire
 * Minecraft stack, rather than the RakNet latency. It has other usages too, such as the ability to
 * be used as some kind of acknowledgement packet, to know when the client has received a certain
 * other packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class NetworkStackLatencyPacket implements BedrockPacket {
    private long timestamp;
    /**
     * @since v332
     */
    private boolean fromServer;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NETWORK_STACK_LATENCY;
    }

    @Override
    public NetworkStackLatencyPacket clone() {
        try {
            return (NetworkStackLatencyPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
