package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ServerboundLoadingScreenPacketType;

/**
 * Sent by the client to tell the server about the state of the loading screen that the client is
 * currently displaying.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundLoadingScreenPacket implements BedrockPacket {
    /**
     * The loading screen event being reported by the client.
     */
    private ServerboundLoadingScreenPacketType type;
    /**
     * The optional loading screen ID associated with {@link #type}.
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
