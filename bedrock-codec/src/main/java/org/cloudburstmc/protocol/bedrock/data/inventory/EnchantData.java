package org.cloudburstmc.protocol.bedrock.data.inventory;

/**
 * Represents enchant data used in the Bedrock protocol.
 *
 * @param type  The type.
 * @param level The level.
 */
public record EnchantData(int type, int level) {
}
