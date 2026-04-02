package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server when an Education Edition camera captures an image for a player.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraPacket implements BedrockPacket {
    /**
     * The unique ID of the camera entity that took the picture.
     */
    private long cameraUniqueEntityId;
    /**
     * The unique ID of the player targeted by the camera capture.
     */
    private long playerUniqueEntityId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA;
    }

    @Override
    public CameraPacket clone() {
        try {
            return (CameraPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
