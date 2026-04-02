package org.cloudburstmc.protocol.bedrock.data.inventory;

import java.util.List;

/**
 * A single enchantment-table option shown to the client.
 *
 * @param cost         The XP level cost required to pick this option.
 * @param primarySlot  The primary enchantment slot index used by the client for this option.
 * @param enchants0    The first group of enchantments that may be applied by this option.
 * @param enchants1    The second group of enchantments that may be applied by this option.
 * @param enchants2    The third group of enchantments that may be applied by this option.
 * @param enchantName  The untranslated phrase rendered by the client in Standard Galactic Alphabet.
 * @param enchantNetId The network ID sent back by the client when this option is selected.
 */
public record EnchantOptionData(int cost, int primarySlot, List<EnchantData> enchants0, List<EnchantData> enchants1, List<EnchantData> enchants2,
                                String enchantName, int enchantNetId) {
}
