package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.protocol.bedrock.data.camera.AimAssistAction;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to send a player animation from one player to all viewers of that player. It
 * is used for a couple of actions, such as arm swimming and critical hits.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraAimAssistInstructionPacket implements BedrockPacket {
    private String presetId;
    private AimAssistAction action;
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
