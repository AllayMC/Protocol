package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make the client start video streaming. The client starts sending
 * screenshots at the configured rate to the websocket server supplied in this packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class VideoStreamConnectPacket implements BedrockPacket {
    private String address;
    private float screenshotFrequency;
    private Action action;
    /**
     * @since v361
     */
    private int width;
    /**
     * @since v361
     */
    private int height;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.VIDEO_STREAM_CONNECT;
    }

    public enum Action {
        OPEN,
        CLOSE
    }

    @Override
    public VideoStreamConnectPacket clone() {
        try {
            return (VideoStreamConnectPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
