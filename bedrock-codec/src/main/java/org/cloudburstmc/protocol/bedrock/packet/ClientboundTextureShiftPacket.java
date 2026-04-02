package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * Sent by the server to control client-side texture shift collections.
 *
 * @since v924
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundTextureShiftPacket implements BedrockPacket {

    /**
     * The texture shift action to perform.
     */
    private Action action;
    /**
     * The name of the texture shift collection.
     */
    private String collectionName;
    /**
     * The step to shift from.
     */
    private String fromStep;
    /**
     * The step to shift to.
     */
    private String toStep;
    /**
     * The complete ordered step list for the collection.
     */
    private List<String> allSteps;
    /**
     * The current length of the shift in ticks.
     */
    private long currentLengthTicks;
    /**
     * The total length of the shift in ticks.
     */
    private long totalLengthTicks;
    /**
     * Specifies if the texture shift is enabled.
     */
    private boolean enabled;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_TEXTURE_SHIFT;
    }

    @Override
    public ClientboundTextureShiftPacket clone() {
        try {
            return (ClientboundTextureShiftPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum Action {
        INVALID,
        INITIALIZE,
        START,
        SET_ENABLED,
        SYNC
    }
}
