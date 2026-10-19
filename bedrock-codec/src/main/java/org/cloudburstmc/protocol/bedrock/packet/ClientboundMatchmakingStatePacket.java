package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Informs the client when its matchmaking state changes.
 *
 * @since v2225
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundMatchmakingStatePacket implements BedrockPacket {

    private MatchmakingState state;
    private String destinationName;
    /**
     * Optional trigger details. A null value omits the entire options structure;
     * a present structure can have both components absent.
     */
    @Nullable
    private MatchmakingStateOptions options;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_MATCHMAKING_STATE;
    }

    @Override
    public ClientboundMatchmakingStatePacket clone() {
        try {
            return (ClientboundMatchmakingStatePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Matchmaking progress and termination states.
     *
     * @since v2225
     */
    public enum MatchmakingState {
        IDLE,
        MATCHMAKING,
        MATCH_FOUND,
        CANCELED,
        PLAYER_LEFT_PARTY,
        PLAYER_LEFT_SERVER,
        SERVER_SHUTDOWN,
        TIMED_OUT,
        REQUEUE_AS_PARTY,
    }

    /**
     * Details of the player who triggered matchmaking. Each component has its own
     * presence flag; an absent Boolean is distinct from {@code false}.
     *
     * @param triggeringPlayerName   the player name, or null when absent; an empty name is a present value
     * @param triggeredByLocalPlayer whether the local player triggered matchmaking, or null when absent
     * @since v2225
     */
    public record MatchmakingStateOptions(@Nullable String triggeringPlayerName,
                                          @Nullable Boolean triggeredByLocalPlayer) {
    }
}
