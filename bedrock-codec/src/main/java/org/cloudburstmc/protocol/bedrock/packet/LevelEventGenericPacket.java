package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to send a 'generic' level event to the client. This packet sends an NBT
 * serialised object and may for that reason be used for any event holding additional data.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LevelEventGenericPacket implements BedrockPacket {
    private LevelEventType type;
    private Object tag;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_EVENT_GENERIC;
    }

    @Override
    public LevelEventGenericPacket clone() {
        try {
            return (LevelEventGenericPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
