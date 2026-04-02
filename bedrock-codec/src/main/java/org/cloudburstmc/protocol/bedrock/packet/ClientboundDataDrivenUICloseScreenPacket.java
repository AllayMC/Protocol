package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to close one or more data-driven UI screens on the client.
 *
 * @since v924
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundDataDrivenUICloseScreenPacket implements BedrockPacket {
    /**
     * The unique ID of the form to close. If absent, all data-driven UI forms are closed.
     *
     * @since v944
     */
    @Nullable
    private Integer formId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_DATA_DRIVEN_UI_CLOSE_SCREEN;
    }

    @Override
    public ClientboundDataDrivenUICloseScreenPacket clone() {
        try {
            return (ClientboundDataDrivenUICloseScreenPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
