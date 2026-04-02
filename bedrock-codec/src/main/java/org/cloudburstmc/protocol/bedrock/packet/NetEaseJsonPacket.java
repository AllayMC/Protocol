package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.annotation.NetEaseOnly;

/**
 * Carries NetEase-specific JSON payloads.
 */
@NetEaseOnly
@Data
@EqualsAndHashCode(doNotUseGetters = true)
public class NetEaseJsonPacket implements BedrockPacket {
    private String json;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.NET_EASE_JSON;
    }

    @Override
    public NetEaseJsonPacket clone() {
        try {
            return (NetEaseJsonPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
