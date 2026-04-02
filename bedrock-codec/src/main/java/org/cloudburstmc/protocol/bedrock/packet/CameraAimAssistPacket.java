package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.protocol.bedrock.data.camera.AimAssistAction;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client to set up aim assist for the client's camera.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraAimAssistPacket implements BedrockPacket {
    /**
     * The maximum angle around the player's cursor that aim assist should search when
     * {@link #targetMode} is {@link TargetMode#ANGLE}.
     */
    private Vector2f viewAngle;
    /**
     * The maximum distance from the player's cursor should check for a target, if TargetMode is set
     * to protocol.AimAssistTargetModeDistance.
     */
    private float distance;
    /**
     * The mode that the camera should use for detecting targets. This is currently one of
     * protocol.AimAssistTargetModeAngle or protocol.AimAssistTargetModeDistance.
     */
    private TargetMode targetMode;
    /**
     * The action to perform with aim assist, such as setting or clearing it.
     */
    private AimAssistAction action;
    /**
     * The identifier of the preset previously defined in {@link CameraAimAssistPresetsPacket}.
     *
     * @since v766
     */
    private String presetId;
    /**
     * Specifies if debug render should be shown.
     *
     * @since v827
     */
    private boolean showDebugRender;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_AIM_ASSIST;
    }

    @Override
    public CameraAimAssistPacket clone() {
        try {
            return (CameraAimAssistPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum TargetMode {
        ANGLE,
        DISTANCE
    }
}
