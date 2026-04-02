package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Used to communicate custom messages from the client to the server, or from the server to the
 * client. While the name may suggest this packet is used for the discontinued scripting API, it is
 * likely instead for the GameTest framework.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ScriptMessagePacket implements BedrockPacket {

    /**
     * The identifier of the script message. Both sides use this value to decide how to interpret
     * {@link #message}.
     */
    private String channel;
    /**
     * The payload of the message. Despite the Java field being a string, this represents opaque
     * application-defined data.
     */
    private String message;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SCRIPT_MESSAGE;
    }

    @Override
    public ScriptMessagePacket clone() {
        try {
            return (ScriptMessagePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
