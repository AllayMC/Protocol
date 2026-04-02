package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;

/**
 * Sent by the server to report the player's position after server-side movement processing.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerPlayerPostMovePositionPacket implements BedrockPacket {
    /**
     * The resulting player position.
     */
    private Vector3f position;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVER_PLAYER_POST_MOVE_POSITION;
    }

    @Override
    public ServerPlayerPostMovePositionPacket clone() {
        try {
            return (ServerPlayerPostMovePositionPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
