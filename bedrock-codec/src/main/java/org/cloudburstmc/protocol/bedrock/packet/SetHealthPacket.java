package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server. It sets the health of the player it is sent to. The SetHealth packet should
 * no longer be used. Instead, the health attribute should be used so that the health and maximum
 * health may be changed directly.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetHealthPacket implements BedrockPacket {
    private int health;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_HEALTH;
    }

    @Override
    public SetHealthPacket clone() {
        try {
            return (SetHealthPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
