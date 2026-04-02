package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

/**
 * ItemType represents a consistent combination of network ID and metadata value of an item. It
 * cannot usually be changed unless a new item is obtained.
 */
public enum ItemDescriptorType {
    INVALID,
    DEFAULT,
    MOLANG,
    ITEM_TAG,
    DEFERRED,
    /**
     * @since v575
     */
    COMPLEX_ALIAS
}
