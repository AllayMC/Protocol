package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to resynchronize the client's world time. The client continues advancing time
 * locally, so this packet is only needed when the server wants to correct or jump the value.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetTimePacket implements BedrockPacket {
    /**
     * The current world time in ticks. The value is not limited to the 0-23999 day cycle range.
     */
    private int time;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_TIME;
    }

    @Override
    public SetTimePacket clone() {
        try {
            return (SetTimePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
