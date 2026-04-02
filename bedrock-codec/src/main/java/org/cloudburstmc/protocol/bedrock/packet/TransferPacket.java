package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to transfer a player from the current server to another. Doing so will fully
 * disconnect the client, bring it back to the main menu and make it connect to the next server.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TransferPacket implements BedrockPacket {
    /**
     * The address of the new server, which might be either a hostname or an actual IP address.
     */
    private String address;
    /**
     * The UDP port of the new server.
     */
    private int port;
    /**
     * A flag with currently unknown purpose that was added alongside newer transfer behaviour.
     *
     * @since v729
     */
    private boolean reloadWorld;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TRANSFER;
    }

    @Override
    public TransferPacket clone() {
        try {
            return (TransferPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
