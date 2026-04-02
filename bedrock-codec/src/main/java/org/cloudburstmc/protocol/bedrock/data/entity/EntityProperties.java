package org.cloudburstmc.protocol.bedrock.data.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

/**
 * EntityProperties holds lists of entity properties that define specific attributes of an entity.
 * As of v1.19.40, the vanilla server does not use these properties, however they are still
 * supported by the protocol.
 *
 * @param intProperties   The int properties.
 * @param floatProperties A list of entity properties that contain float values.
 */
public record EntityProperties(List<IntEntityProperty> intProperties, List<FloatEntityProperty> floatProperties) {

    public EntityProperties() {
        this(new ObjectArrayList<>(), new ObjectArrayList<>());
    }
}
