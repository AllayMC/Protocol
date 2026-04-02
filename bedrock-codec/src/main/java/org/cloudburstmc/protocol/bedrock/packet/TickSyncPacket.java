package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Used by authoritative movement to synchronize a timestamp exchange between the client and
 * server. The initiating side sends a request timestamp, and the reply echoes that value together
 * with the responder's timestamp.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TickSyncPacket implements BedrockPacket {
    /**
     * The timestamp chosen by the side initiating the sync exchange. A response echoes this value
     * so the request can be matched.
     */
    private long requestTimestamp;
    /**
     * The timestamp supplied by the side answering the sync exchange.
     */
    private long responseTimestamp;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TICK_SYNC;
    }

    @Override
    public TickSyncPacket clone() {
        try {
            return (TickSyncPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
