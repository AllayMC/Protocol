package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;

/**
 * Sent by the server to the client to either update a player's position on the locator bar, or
 * remove them completely. The client will determine how to render the player on the locator bar
 * based on their own distance to Position.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerLocationPacket implements BedrockPacket {

    /**
     * The locator-bar action to perform for the target player.
     */
    private Type type;
    /**
     * EntityUniqueID is the unique ID of the entity. The unique ID is a value that remains
     * consistent across different sessions of the same world.
     */
    private long targetEntityId;
    /**
     * The position of the player to be used on the locator bar. This is only set when the Type is
     * PlayerLocationTypeCoordinates.
     */
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
