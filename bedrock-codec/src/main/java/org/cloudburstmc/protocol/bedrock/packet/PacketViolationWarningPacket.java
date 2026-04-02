package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.PacketViolationSeverity;
import org.cloudburstmc.protocol.bedrock.data.PacketViolationType;

/**
 * Sent by the client when it receives an invalid packet from the server. It holds some information
 * on the error that occurred.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PacketViolationWarningPacket implements BedrockPacket {
    /**
     * The category of packet violation that the client detected.
     */
    private PacketViolationType type;
    /**
     * Specifies the severity of the packet violation. The action the client takes after this
     * violation depends on the severity sent.
     */
    private PacketViolationSeverity severity;
    /**
     * PacketID is the ID of the invalid packet that was received.
     */
    private int packetCauseId;
    /**
     * The context.
     */
    private String context;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PACKET_VIOLATION_WARNING;
    }

    @Override
    public PacketViolationWarningPacket clone() {
        try {
            return (PacketViolationWarningPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
