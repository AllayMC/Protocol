package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to tell the client which entity type most recently damaged the player.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetLastHurtByPacket implements BedrockPacket {
    /**
     * The numeric entity type ID of the last attacker.
     */
    private int entityTypeId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_LAST_HURT_BY;
    }

    @Override
    public SetLastHurtByPacket clone() {
        try {
            return (SetLastHurtByPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
