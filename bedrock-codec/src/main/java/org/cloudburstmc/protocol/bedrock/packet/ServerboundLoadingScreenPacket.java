package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ServerboundLoadingScreenPacketType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to tell the server about the state of the loading screen that the client is
 * currently displaying.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundLoadingScreenPacket implements BedrockPacket {
    private ServerboundLoadingScreenPacketType type;
    /**
     * Optional int, not present if null
     */
    private Integer loadingScreenId;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_LOADING_SCREEN;
    }

    @Override
    public ServerboundLoadingScreenPacket clone() {
        try {
            return (ServerboundLoadingScreenPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
