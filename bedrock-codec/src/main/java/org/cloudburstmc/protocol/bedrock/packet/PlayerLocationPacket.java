package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client to either update a player's position on the locator bar, or
 * remove them completely. The client will determine how to render the player on the locator bar
 * based on their own distance to Position.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerLocationPacket implements BedrockPacket {

    private Type type;
    private long targetEntityId;
    private Vector3f position;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_LOCATION;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (PlayerLocationPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum Type {
        COORDINATES,
        HIDE
    }
}
