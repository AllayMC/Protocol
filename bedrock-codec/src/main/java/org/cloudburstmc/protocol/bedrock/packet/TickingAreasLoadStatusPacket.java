package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to notify the client of a ticking area's loading status.
 *
 * @since v503
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TickingAreasLoadStatusPacket implements BedrockPacket {
    /**
     * {@code true} if the server is still waiting for the area's preload to complete.
     */
    boolean waitingForPreload;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TICKING_AREAS_LOAD_STATUS;
    }

    @Override
    public TickingAreasLoadStatusPacket clone() {
        try {
            return (TickingAreasLoadStatusPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
