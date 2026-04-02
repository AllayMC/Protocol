package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to despawn an entity that is currently known to the client. Sending this
 * packet for an entity that is not present client-side has no effect.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RemoveEntityPacket implements BedrockPacket {
    /**
     * The unique entity ID of the entity to remove. Bedrock serializes the unique ID here even
     * though most runtime interaction packets use the session-scoped runtime entity ID.
     */
    private long uniqueEntityId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REMOVE_ENTITY;
    }

    @Override
    public RemoveEntityPacket clone() {
        try {
            return (RemoveEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
