package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.clock.SyncWorldClocksPayload;

/**
 * Sent by the server to initialize or update client-side world clocks.
 *
 * @since v944
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SyncWorldClocksPacket implements BedrockPacket {

    /**
     * The clock payload describing the requested synchronization action.
     */
    private SyncWorldClocksPayload data;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SYNC_WORLD_CLOCKS;
    }

    @Override
    public SyncWorldClocksPacket clone() {
        try {
            return (SyncWorldClocksPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
