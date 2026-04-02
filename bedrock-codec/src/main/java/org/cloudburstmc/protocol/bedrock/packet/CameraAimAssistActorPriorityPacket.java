package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.camera.AimAssistActorPriorityData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to define actor-specific aim assist priorities on the client.
 *
 * @since v924
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraAimAssistActorPriorityPacket implements BedrockPacket {

    /**
     * The aim assist priority entries to apply.
     */
    private List<AimAssistActorPriorityData> priorityData;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_AIM_ASSIST_ACTOR_PRIORITY;
    }

    @Override
    public CameraAimAssistActorPriorityPacket clone() {
        try {
            return (CameraAimAssistActorPriorityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
