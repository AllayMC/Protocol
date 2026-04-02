package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;

/**
 * Sent by the client to ask the server to refresh the player's entitlements for the current
 * session. This packet has no fields.
 */
@Data
public class RefreshEntitlementsPacket implements BedrockPacket {

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REFRESH_ENTITLEMENTS;
    }

    @Override
    public RefreshEntitlementsPacket clone() {
        try {
            return (RefreshEntitlementsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
