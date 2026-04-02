package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.AttributeData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityProperties;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to the client to spawn an entity to the player. It is used for every entity
 * except other players, for which the AddPlayer packet is used.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AddEntityPacket implements BedrockPacket {
    /**
     * A list of attributes that the entity has. It includes attributes such as its health,
     * movement speed, etc.
     */
    private List<AttributeData> attributes = new ObjectArrayList<>();
    /**
     * A map of entity metadata, which includes flags and data properties that alter in particular
     * the way the entity looks. Flags include ones such as 'on fire' and 'sprinting'. The metadata
     * values are indexed by their property key.
     */
    private EntityDataMap metadata = new EntityDataMap();
    /**
     * A list of entity links that are currently active on the entity. These links alter the way the
     * entity shows up when first spawned in terms of it shown as riding an entity. Setting these
     * links is important for new viewers to see the entity is riding another entity.
     */
    private List<EntityLinkData> entityLinks = new ObjectArrayList<>();
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
     * The legacy numeric type ID of the entity. Newer protocol versions primarily rely on {@link
     * #identifier}, but older codecs still serialise this numeric ID.
     */
    private int entityType;
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
     * The entity pitch and yaw, in degrees. Pitch is the vertical rotation and yaw is the
     * horizontal rotation.
     */
    private Vector2f rotation;
    /**
     * The yaw of the entity head, in degrees. If this differs from {@link #rotation}, the head is
     * rendered turned relative to the body.
     */
    private float headRotation;
    /**
     * The string entity type of the entity, for example 'minecraft:skeleton'. A list of these
     * entities may be found online.
     *
     * @since v313
     */
    private String identifier;
    /**
     * The yaw of the entity body, in degrees. This can differ from the regular yaw and head yaw to
     * render the body turned independently.
     *
     * @since v534
     */
    private float bodyRotation;
    /**
     * A list of properties that the entity inhibits. These properties define and alter specific
     * attributes of the entity.
     *
     * @since v557
     */
    private final EntityProperties properties = new EntityProperties();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_ENTITY;
    }

    @Override
    public AddEntityPacket clone() {
        try {
            return (AddEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
