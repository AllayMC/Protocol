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
    /**
     * The video-capture action to perform, such as starting or stopping capture.
     */
    private Action action;
    /**
     * The frame rate at which the video should be recorded. It is only used when Action is
     * PlayerVideoCaptureActionStart. A higher frame rate will cause more frames to be recorded, but
     * also a noticeable increase in lag.
     */
    private int frameRate;
    /**
     * The prefix of the file name that will be used to save the frames. The frames will be saved in
     * the format 'FilePrefix%d.png' where %d is the frame index.
     */
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
