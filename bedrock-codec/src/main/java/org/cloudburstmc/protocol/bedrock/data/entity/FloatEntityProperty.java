package org.cloudburstmc.protocol.bedrock.data.entity;

/**
 * Represents an entity property that contains a float value.
 *
 * @param index Represents the index of the property. It is unclear what the exact purpose of this is.
 * @param value The value of the property.
 */
public record FloatEntityProperty(int index, float value) implements EntityProperty {
}
