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
    private String displaySlot;
    private String objectiveId;
    private CharSequence displayName;
    private String criteria;
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

    public String getDisplayName() {
        return getDisplayName(String.class);
    }

    public <T extends CharSequence> T getDisplayName(Class<T> type) {
        return type.cast(displayName);
    }
}
