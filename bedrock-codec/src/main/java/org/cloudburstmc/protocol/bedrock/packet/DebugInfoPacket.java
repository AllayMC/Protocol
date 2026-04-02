package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * A packet sent by the server to the client. It does not seem to do anything when sent to the
 * normal client in 1.16.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class DebugInfoPacket implements BedrockPacket {
    /**
     * PlayerUniqueID is the unique ID of the player that the packet is sent to.
     */
    private long uniqueEntityId;
    /**
     * The opaque debug payload sent to the target client.
     */
    private String data;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.DEBUG_INFO;
    }

    @Override
    public DebugInfoPacket clone() {
        try {
            return (DebugInfoPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
