package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.event.EventData;

/**
 * Sent by the server to send an event with additional data. It is typically sent to the client for
 * telemetry reasons, much like the SimpleEvent packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EventPacket implements BedrockPacket {
    /**
     * The unique entity ID associated with the event, typically the player the telemetry record is
     * about.
     */
    private long uniqueEntityId;
    /**
     * An additional protocol flag carried alongside the event payload. Its exact effect is still
     * unknown.
     */
    private boolean usePlayerId;
    /**
     * The concrete event payload. The runtime type determines both the event kind and the fields
     * serialized after it.
     */
    private EventData eventData;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.EVENT;
    }

    @Override
    public EventPacket clone() {
        try {
            return (EventPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
