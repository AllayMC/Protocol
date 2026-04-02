package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to display an object as a scoreboard to the player. Once sent, it should be
 * followed up by a SetScore packet to set the lines of the packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetDisplayObjectivePacket implements BedrockPacket {
    /**
     * The display slot in which the scoreboard should appear, for example {@code list},
     * {@code sidebar}, or {@code belowname}.
     */
    private String displaySlot;
    /**
     * The objective ID.
     */
    private String objectiveId;
    /**
     * The name, or title, that is displayed at the top of the scoreboard.
     */
    private String displayName;
    /**
     * The criteria.
     */
    private String criteria;
    /**
     * The sort order used for scoreboard entries.
     */
    private int sortOrder;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_DISPLAY_OBJECTIVE;
    }

    @Override
    public SetDisplayObjectivePacket clone() {
        try {
            return (SetDisplayObjectivePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
