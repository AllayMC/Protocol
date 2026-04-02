package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.camera.*;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

/**
 * Sent by the server to apply one or more custom camera instructions.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraInstructionPacket implements BedrockPacket {
    /**
     * A camera instruction that sets the active camera preset and optional transform parameters.
     */
    private CameraSetInstruction setInstruction;
    /**
     * A camera instruction that fades the screen to a specific colour.
     */
    private CameraFadeInstruction fadeInstruction;
    /**
     * Clear can be set to true to clear all the current camera instructions.
     */
    private OptionalBoolean clear = OptionalBoolean.empty();
    /**
     * A camera instruction that targets a specific entity.
     *
     * @since v712
     */
    private CameraTargetInstruction targetInstruction;
    /**
     * RemoveTarget can be set to true to remove the current aim assist target.
     *
     * @since v712
     */
    private OptionalBoolean removeTarget = OptionalBoolean.empty();
    /**
     * A camera instruction that updates the field of view.
     *
     * @since v827
     */
    private CameraFovInstruction fovInstruction;
    /**
     * A camera instruction that creates a spline path for the camera to follow.
     *
     * @since v859
     */
    private CameraSplineInstruction splineInstruction;
    /**
     * A camera instruction that attaches the camera to an entity.
     *
     * @since v859
     */
    private CameraAttachToEntityInstruction attachInstruction;
    /**
     * DetachFromEntity can be set to true to detach the camera from the current entity.
     *
     * @since v859
     */
    private OptionalBoolean detachFromEntity = OptionalBoolean.empty();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_INSTRUCTION;
    }

    public void setClear(boolean value) {
        this.clear = OptionalBoolean.of(value);
    }

    public void setClear(OptionalBoolean clear) {
        this.clear = clear;
    }

    @Override
    public CameraInstructionPacket clone() {
        try {
            return (CameraInstructionPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
