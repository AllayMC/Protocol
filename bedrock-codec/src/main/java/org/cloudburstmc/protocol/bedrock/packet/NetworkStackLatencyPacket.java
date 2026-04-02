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
    /**
     * The timestamp of the network stack latency packet. The client will, if NeedsResponse is set
     * to true, send a NetworkStackLatency packet with this same timestamp packet in response.
     */
    private long timestamp;
    /**
     * Specifies if the other side should reply with a {@code NetworkStackLatencyPacket} carrying the
     * same timestamp. Despite the field name, this flag is a response request rather than an
     * indicator of who sent the packet.
     *
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
