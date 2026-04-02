package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.DisconnectFailReason;

/**
 * Sent by the server to disconnect the client, optionally showing a message on the disconnection
 * screen.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class DisconnectPacket implements BedrockPacket {
    /**
     * Specifies if the disconnection screen should be hidden and the client should return straight
     * to the main menu.
     */
    private boolean messageSkipped;
    /**
     * The message shown on the disconnection screen when the screen is not skipped.
     */
    private String kickMessage;
    /**
     * The reason for the disconnection. This affects the error code displayed on the Ore UI
     * disconnection screen.
     *
     * @since v622
     */
    private DisconnectFailReason reason = DisconnectFailReason.UNKNOWN;
    /**
     * A filtered version of Message with all the profanity removed. The client will use this over
     * Message if this field is not empty and they have the "Filter Profanity" setting enabled.
     *
     * @since v712
     */
    private String filteredMessage = "";

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.DISCONNECT;
    }

    @Override
    public DisconnectPacket clone() {
        try {
            return (DisconnectPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
