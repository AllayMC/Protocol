package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to let the client know what entity type it was last hurt by. At this moment,
 * the packet is useless and should not be used. There is no behavior that depends on if this packet
 * is sent or not.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetLastHurtByPacket implements BedrockPacket {
    private int entityTypeId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_LAST_HURT_BY;
    }

    @Override
    public SetLastHurtByPacket clone() {
        try {
            return (SetLastHurtByPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
