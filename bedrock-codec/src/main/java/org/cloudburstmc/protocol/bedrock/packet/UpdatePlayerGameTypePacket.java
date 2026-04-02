package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.GameType;

/**
 * Sent by the server to change the game mode of a player. It is functionally identical to the
 * SetPlayerGameType packet.
 */
@Data
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
public class UpdatePlayerGameTypePacket implements BedrockPacket {
    /**
     * The new game type of the player. Some game types require matching ability flags in
     * {@link UpdateAbilitiesPacket} for the client to expose their full behaviour.
     */
    private GameType gameType;
    /**
     * The unique ID of the player whose game type should be updated.
     */
    private long entityId;
    /**
     * The server tick at which the packet was sent. It is used in relation to
     * CorrectPlayerMovePrediction.
     *
     * @since v671
     */
    private long tick;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_PLAYER_GAME_TYPE;
    }

    @Override
    public UpdatePlayerGameTypePacket clone() {
        try {
            return (UpdatePlayerGameTypePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
