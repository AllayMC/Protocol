package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraSplineDefinition;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to register named camera spline definitions on the client.
 *
 * @since v924
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraSplinePacket implements BedrockPacket {

    /**
     * The spline definitions available for later camera playback.
     */
    private List<CameraSplineDefinition> splines;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_SPLINE;
    }

    @Override
    public CameraSplinePacket clone() {
        try {
            return (CameraSplinePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
