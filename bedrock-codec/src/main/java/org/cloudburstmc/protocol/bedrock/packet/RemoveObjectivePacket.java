package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to remove a scoreboard objective. It is used to stop showing a scoreboard to a
 * player.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RemoveObjectivePacket implements BedrockPacket {
    /**
     * The scoreboard objective name to remove. It must match the identifier previously used in
     * {@link SetDisplayObjectivePacket}.
     */
    private String objectiveId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REMOVE_OBJECTIVE;
    }

    @Override
    public RemoveObjectivePacket clone() {
        try {
            return (RemoveObjectivePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
