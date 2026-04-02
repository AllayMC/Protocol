package org.cloudburstmc.protocol.bedrock.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;

/**
 * Represents an object on a map that is 'tracked' by the client, such as an entity or a block. This
 * object may move, which is handled client-side.
 */
@Getter
@ToString
@EqualsAndHashCode
public class MapTrackedObject {
    /**
     * The type of the tracked object. It is either MapObjectTypeEntity or MapObjectTypeBlock.
     */
    private final Type type;
    /**
     * EntityUniqueID is the unique ID of the entity, if the tracked object was an entity. It needs
     * not to be filled out if Type is not MapObjectTypeEntity.
     */
    private long entityId;
    /**
     * The block position of the tracked object when {@link #type} is {@link Type#BLOCK}.
     */
    private Vector3i position;

    public MapTrackedObject(long entityId) {
        this.type = Type.ENTITY;
        this.entityId = entityId;
    }

    public MapTrackedObject(Vector3i position) {
        this.type = Type.BLOCK;
        this.position = position;
    }

    public enum Type {
        ENTITY,
        BLOCK
    }
}
