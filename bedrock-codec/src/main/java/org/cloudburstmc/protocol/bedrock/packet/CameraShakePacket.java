package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.CameraShakeAction;
import org.cloudburstmc.protocol.bedrock.data.CameraShakeType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Causes the client's camera view to shake with a specified intensity and duration.
 *
 * <p>No known uses yet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraShakePacket implements BedrockPacket {
    /**
     * The intensity of the camera shake. The client clamps this value to 4, so higher values may
     * not have any additional effect.
     */
    private float intensity;
    /**
     * The duration of the shake in seconds.
     */
    private float duration;

    /**
     * The type of shake to apply, which affects how the shake is rendered client-side.
     */
    private CameraShakeType shakeType;
    /**
     * The action to perform, typically either adding shake or stopping it.
     *
     * @since v428
     */
    private CameraShakeAction shakeAction;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_SHAKE;
    }

    @Override
    public CameraShakePacket clone() {
        try {
            return (CameraShakePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
