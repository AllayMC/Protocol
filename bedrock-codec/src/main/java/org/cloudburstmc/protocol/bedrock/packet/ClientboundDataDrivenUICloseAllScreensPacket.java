package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Allows the server to tell the client to close all the Data Driven UI screens.
 *
 * @since v924
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundDataDrivenUICloseAllScreensPacket implements BedrockPacket {

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_DATA_DRIVEN_UI_CLOSE_ALL_SCREENS;
    }

    @Override
    public ClientboundDataDrivenUICloseAllScreensPacket clone() {
        try {
            return (ClientboundDataDrivenUICloseAllScreensPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
