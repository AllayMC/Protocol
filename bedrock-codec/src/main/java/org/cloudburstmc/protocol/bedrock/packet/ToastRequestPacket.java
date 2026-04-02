package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * A packet sent from the server to the client to display a toast to the top of the screen. These
 * toasts are the same as the ones seen when, for example, loading a new resource pack or obtaining
 * an achievement.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ToastRequestPacket implements BedrockPacket {

    /**
     * The title of the toast.
     */
    private String title;
    /**
     * The message shown below the toast title.
     */
    private String content;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TOAST_REQUEST;
    }

    @Override
    public ToastRequestPacket clone() {
        try {
            return (ToastRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
