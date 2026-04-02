package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.AuthoritativeMovementMode;

/**
 * Sent by the server to change which side is authoritative for player movement.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetMovementAuthorityPacket implements BedrockPacket {
    /**
     * The movement authority mode to use.
     */
    private AuthoritativeMovementMode movementMode;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_MOVEMENT_AUTHORITY;
    }

    @Override
    public SetMovementAuthorityPacket clone() {
        try {
            return (SetMovementAuthorityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
