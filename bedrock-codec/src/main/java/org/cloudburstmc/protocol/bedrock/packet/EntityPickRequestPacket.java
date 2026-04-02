package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the client when it tries to pick an entity, so that it gets a spawn egg which can spawn
 * that entity.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EntityPickRequestPacket implements BedrockPacket {
    /**
     * The unique ID of the entity that the client attempted to pick so the server can resolve the
     * correct spawn egg.
     */
    private long runtimeEntityId;
    /**
     * The hotbar slot that was selected when the pick request was made. If the slot is empty, the
     * resulting spawn egg is typically placed there.
     */
    private int hotbarSlot;
    /**
     * Specifies whether the client also wants the picked entity's metadata copied into the spawn
     * egg item.
     *
     * @since v465
     */
    private boolean withData;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ENTITY_PICK_REQUEST;
    }

    @Override
    public EntityPickRequestPacket clone() {
        try {
            return (EntityPickRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
