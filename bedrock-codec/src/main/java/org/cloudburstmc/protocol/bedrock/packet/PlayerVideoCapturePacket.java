package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Packet is sent by the server to start or stop video recording for a player. This packet only
 * works on development builds and has no effect on retail builds. When recording, the client will
 * save individual frames to '/LocalCache/minecraftpe' in the format specified below.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerVideoCapturePacket implements BedrockPacket {
    private Action action;
    private int frameRate;
    private String filePrefix;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_VIDEO_CAPTURE;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (PlayerVideoCapturePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum Action {
        STOP_VIDEO_CAPTURE,
        START_VIDEO_CAPTURE,
        UNKNOWN
    }
}
