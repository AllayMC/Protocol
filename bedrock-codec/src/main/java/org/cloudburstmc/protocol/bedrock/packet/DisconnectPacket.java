package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.DisconnectFailReason;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * May be sent by the server to disconnect the client using an optional message to send as the
 * disconnect screen.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class DisconnectPacket implements BedrockPacket {
    /**
     * @since v622
     */
    private DisconnectFailReason reason = DisconnectFailReason.UNKNOWN;
    private boolean messageSkipped;
    private String kickMessage;
    /**
     * @since v712
     */
    private String filteredMessage = "";

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.DISCONNECT;
    }

    @Override
    public DisconnectPacket clone() {
        try {
            return (DisconnectPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
