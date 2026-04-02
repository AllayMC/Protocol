package org.cloudburstmc.protocol.bedrock.data.entity;

/**
 * IntegerEntityProperty is an entity property that contains an integer value.
 *
 * @param index Represents the index of the property. It is unclear what the exact purpose of this is.
 * @param value The value of the property.
 */
public record IntEntityProperty(int index, int value) implements EntityProperty {
}
