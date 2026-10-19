package org.cloudburstmc.protocol.bedrock.data.inventory;

/**
 * The hand used by an inventory transaction.
 * Animate packets also carry this value since v2225.
 *
 * @since v2192
 */
public enum HandSlot {
    MAINHAND,
    OFFHAND
}
