package org.cloudburstmc.protocol.bedrock.data.entity;

/**
 * Represents entity data type used in the Bedrock protocol.
 */
public class EntityDataType<T> {

    /**
     * The name.
     */
    private final String name;
    /**
     * The type.
     */
    private final Class<?> type;

    public EntityDataType(Class<? super T> type, String name) {
        this.name = name;
        this.type = type;
    }

    public boolean isInstance(Object value) {
        return type.isInstance(value);
    }

    public String getTypeName() {
        return this.type.getTypeName();
    }

    @Override
    public String toString() {
        return name;
    }
}
