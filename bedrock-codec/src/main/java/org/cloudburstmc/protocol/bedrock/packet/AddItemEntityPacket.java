package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client to make an item entity show up. It is one of the few entities
 * that cannot be sent using the {@link AddEntityPacket}
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AddItemEntityPacket implements BedrockPacket {
    /**
     * A map of entity metadata, which includes flags and data properties that alter in particular
     * the way the entity looks. Flags include ones such as 'on fire' and 'sprinting'. The metadata
     * values are indexed by their property key.
     */
    private EntityDataMap metadata = new EntityDataMap();
    /**
     * The unique ID of the entity. The unique ID is a value that remains consistent across
     * different sessions of the same world, but most servers simply fill the runtime ID of the
     * entity out for this field.
     */
    private long uniqueEntityId;
    /**
     * The runtime ID of the entity. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * The item stack represented by the spawned item entity.
     */
    private ItemData itemInHand;
    /**
     * The position to spawn the entity on. If the entity is on a distance that the player cannot
     * see it, the entity will still show up if the player moves closer.
     */
    private Vector3f position;
    /**
     * The initial velocity the entity spawns with. This velocity will initiate client side movement
     * of the entity.
     */
    private Vector3f motion;
    /**
     * Specifies if the item was obtained by fishing it up using a fishing rod. It is not clear why
     * the client needs to know this.
     */
    private boolean fromFishing;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_ITEM_ENTITY;
    }

    @Override
    public AddItemEntityPacket clone() {
        try {
            return (AddItemEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
