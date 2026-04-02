package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;

/**
 * Sent by the server to send a 'generic' level event to the client. This packet sends an NBT
 * serialised object and may for that reason be used for any event holding additional data.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LevelEventGenericPacket implements BedrockPacket {
    /**
     * The generic level event identifier. The NBT payload layout depends on this event type.
     */
    private LevelEventType type;
    /**
     * The network little-endian NBT payload for the event. Unlike many other NBT payloads, this is
     * not wrapped in a root compound by the protocol.
     */
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
