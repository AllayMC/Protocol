package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;

/**
 * Sent by the server when a particular event happens that has to do with an entity. Some of these
 * events are entity-specific, for example a wolf shaking itself dry, but others are used for each
 * entity, such as dying.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EntityEventPacket implements BedrockPacket {
    /**
     * The runtime ID of the entity. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * The entity event to fire. Each value maps to a specific client-side animation or behaviour.
     */
    private EntityEventType type;
    /**
     * Optional event-specific payload. Most entity events leave this at {@code 0}, but some values
     * repurpose it for extra state such as caravan size or spawn metadata.
     */
    private int data;
    /**
     * Optional fire position data used by certain modern entity events.
     *
     * @since v974
     */
    private Vector3f fireAtPosition;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ENTITY_EVENT;
    }

    @Override
    public EntityEventPacket clone() {
        try {
            return (EntityEventPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
