package org.cloudburstmc.protocol.bedrock.data;

/**
 * Flags describing which armour slots are present in {@link org.cloudburstmc.protocol.bedrock.packet.PlayerArmorDamagePacket}.
 */
public enum PlayerArmorDamageFlag {
    /**
     * The helmet slot.
     */
    HELMET,
    /**
     * The chestplate slot.
     */
    CHESTPLATE,
    /**
     * The leggings slot.
     */
    LEGGINGS,
    /**
     * The boots slot.
     */
    BOOTS,
    /**
     * The body armour slot.
     *
     * @since v712
     */
    BODY
}
