package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.camera.AimAssistAction;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to update which aim-assist preset the client should use.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraAimAssistInstructionPacket implements BedrockPacket {
    /**
     * The identifier of the preset to use, as previously defined in
     * {@link CameraAimAssistPresetsPacket}.
     */
    private String presetId;
    /**
     * The action to perform with the preset, such as setting or clearing it.
     */
    private AimAssistAction action;
    /**
     * Whether the client is allowed to use aim assist after applying this instruction.
     */
    private boolean allowAimAssist;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_AIM_ASSIST_INSTRUCTION;
    }

    @Override
    public CameraAimAssistInstructionPacket clone() {
        try {
            return (CameraAimAssistInstructionPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
