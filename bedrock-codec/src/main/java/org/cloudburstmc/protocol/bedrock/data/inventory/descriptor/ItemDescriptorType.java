package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

/**
 * ItemType represents a consistent combination of network ID and metadata value of an item. It
 * cannot usually be changed unless a new item is obtained.
 */
public enum ItemDescriptorType {
    INVALID("empty"),
    DEFAULT("name"),
    MOLANG("molang"),
    ITEM_TAG("item_tag"),
    /**
     * @deprecated since v2168
     */
    DEFERRED("DEFERRED_DEPRECATED"),
    /**
     * @since v575
     * @deprecated since v2168
     */
    COMPLEX_ALIAS("COMPLEX_ALIAS_DEPRECATED");

    private final String serializeName;

    ItemDescriptorType(String serializeName) {
        this.serializeName = serializeName;
    }

    public String getSerializeName() {
        return serializeName;
    }

    public static ItemDescriptorType fromName(String name) {
        for (ItemDescriptorType type : values()) {
            if (type.serializeName.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown item descriptor type: " + name);
    }
}
