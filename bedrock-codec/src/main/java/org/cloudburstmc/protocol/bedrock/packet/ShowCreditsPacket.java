package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to show the Minecraft credits screen to the client. It is typically sent when
 * the player beats the ender dragon and leaves the End.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ShowCreditsPacket implements BedrockPacket {
    /**
     * The runtime entity ID of the player whose credits state is changing.
     */
    private long runtimeEntityId;
    /**
     * Whether to start or end the credits sequence.
     */
    private Status status;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SHOW_CREDITS;
    }

    public enum Status {
        /**
         * Begin showing the credits.
         */
        START_CREDITS,
        /**
         * Close the credits screen.
         */
        END_CREDITS
    }

    @Override
    public ShowCreditsPacket clone() {
        try {
            return (ShowCreditsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
