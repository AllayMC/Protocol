package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to open a data-driven UI screen on the client.
 *
 * @since v924
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundDataDrivenUIShowScreenPacket implements BedrockPacket {
    /**
     * The identifier of the screen to show.
     */
    private String screenId;
    /**
     * The unique ID for this screen instance, used by scripting to track it.
     *
     * @since v944
     */
    private int formId;
    /**
     * The optional ID of the data instance associated with this screen.
     *
     * @since v944
     */
    @Nullable
    private Integer dataInstanceId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_DATA_DRIVEN_UI_SHOW_SCREEN;
    }

    @Override
    public ClientboundDataDrivenUIShowScreenPacket clone() {
        try {
            return (ClientboundDataDrivenUIShowScreenPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
