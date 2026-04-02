package org.cloudburstmc.protocol.bedrock.data.inventory;

import org.cloudburstmc.protocol.bedrock.annotation.NetEaseOnly;

/**
 * Represents enchant data used in the Bedrock protocol.
 *
 * @param type                 The type.
 * @param level                The level.
 * @param modEnchantIdentifier NetEase-only enchant identifier suffix.
 */
public record EnchantData(int type, int level, @NetEaseOnly String modEnchantIdentifier) {
    public EnchantData(int type, int level) {
        this(type, level, "");
    }

    public EnchantData {
        modEnchantIdentifier = modEnchantIdentifier == null ? "" : modEnchantIdentifier;
    }
}
