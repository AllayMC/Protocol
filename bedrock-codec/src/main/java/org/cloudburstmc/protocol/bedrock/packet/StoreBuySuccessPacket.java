package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import org.cloudburstmc.protocol.bedrock.annotation.NetEaseOnly;

/**
 * Sent by NetEase clients and servers when a store purchase succeeds.
 */
@NetEaseOnly
@Data
public class StoreBuySuccessPacket implements BedrockPacket {

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.STORE_BUY_SUCCESS;
    }

    @Override
    public StoreBuySuccessPacket clone() {
        try {
            return (StoreBuySuccessPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
