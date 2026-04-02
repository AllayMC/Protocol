package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

/**
 * Sent by the server to change the identity type of one of the entries on a scoreboard. This is
 * used to change, for example, an entry pointing to a player, to a fake player when it leaves the
 * server, and to change it back to a real player when it joins again. In non-vanilla situations,
 * the packet is quite useless.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetScoreboardIdentityPacket implements BedrockPacket {
    /**
     * A list of all entries in the packet. Each of these entries points to one of the entries on a
     * scoreboard. Depending on ActionType, their identity will either be registered or cleared.
     */
    private final List<Entry> entries = new ObjectArrayList<>();
    /**
     * The type of the action to execute. The action is either ScoreboardIdentityActionRegister to
     * associate an identity with the entry, or ScoreboardIdentityActionClear to remove associations
     * with an entity.
     */
    private Action action;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_SCOREBOARD_IDENTITY;
    }

    public enum Action {
        ADD,
        REMOVE
    }

    public record Entry(
            /**
             * The numeric scoreboard entry identifier whose backing identity should be updated.
             */
            long scoreboardId,
            /**
             * The UUID to associate with the scoreboard entry when {@link Action#ADD} is used.
             */
            UUID uuid) {
    }

    @Override
    public SetScoreboardIdentityPacket clone() {
        try {
            return (SetScoreboardIdentityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
