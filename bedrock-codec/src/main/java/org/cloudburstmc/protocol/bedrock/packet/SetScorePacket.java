package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ScoreInfo;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to send the contents of a scoreboard to the player. It may be used to either
 * add, remove or edit entries on the scoreboard.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetScorePacket implements BedrockPacket {
    private Action action;
    private List<ScoreInfo> infos = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_SCORE;
    }

    public enum Action {
        SET,
        REMOVE
    }

    @Override
    public SetScorePacket clone() {
        try {
            return (SetScorePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
