package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.LocatorBarWaypoint;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Sent by the server to add, remove, or update waypoints on the client's locator bar.
 *
 * @since v944
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LocatorBarPacket implements BedrockPacket {

    /**
     * The waypoint updates to apply.
     */
    private List<Payload> waypoints = new ArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LOCATOR_BAR;
    }

    @Override
    public LocatorBarPacket clone() {
        try {
            return (LocatorBarPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public record Payload(Action actionFlag, UUID groupHandle, LocatorBarWaypoint waypoint) {
    }

    public enum Action {
        NONE,
        ADD,
        REMOVE,
        UPDATE
    }
}
