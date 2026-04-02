package org.cloudburstmc.protocol.bedrock.data.inventory;

/**
 * ItemType represents a consistent combination of network ID and metadata value of an item. It
 * cannot usually be changed unless a new item is obtained.
 */
public enum ItemUseType {
    UNKNOWN,
    EQUIP_ARMOR,
    EAT,
    ATTACK,
    CONSUME,
    THROW,
    SHOOT,
    PLACE,
    FILL_BOTTLE,
    FILL_BUCKET,
    POUR_BUCKET,
    USE_TOOL,
    INTERACT,
    RETRIEVED,
    DYED,
    TRADED,
    /**
     * @since 594
     */
    BRUSHING_COMPLETED,
    /**
     * @since v685
     */
    OPENED_VAULT
}
