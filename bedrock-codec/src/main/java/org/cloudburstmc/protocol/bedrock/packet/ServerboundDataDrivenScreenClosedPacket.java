package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent from the client to the server when a data driven screen is closed.
 *
 * @since v944
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundDataDrivenScreenClosedPacket implements BedrockPacket {

    /**
     * The optional instance ID of the screen that was closed.
     */
    private Integer formId;
    /**
     * The reason the client closed the screen.
     */
    private CloseReason closeReason;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_DATA_DRIVEN_SCREEN_CLOSED;
    }

    @Override
    public ServerboundDataDrivenScreenClosedPacket clone() {
        try {
            return (ServerboundDataDrivenScreenClosedPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum CloseReason {
        /**
         * The screen was closed programmatically.
         */
        PROGRAMMATIC_CLOSE,
        /**
         * All open data-driven screens were closed programmatically.
         */
        PROGRAMMATIC_CLOSE_ALL,
        /**
         * The client canceled the screen.
         */
        CLIENT_CANCELED,
        /**
         * The client was unable to open the screen because it was already busy.
         */
        USER_BUSY,
        /**
         * The referenced form or screen instance was invalid.
         */
        INVALID_FORM,
    }
}
