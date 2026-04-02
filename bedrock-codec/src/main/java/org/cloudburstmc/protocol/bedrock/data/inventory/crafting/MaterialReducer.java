package org.cloudburstmc.protocol.bedrock.data.inventory.crafting;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;

/**
 * Represents a craft in a material reducer block in education edition.
 *
 * @param inputId    The input ID.
 * @param itemCounts The item counts.
 */
public record MaterialReducer(int inputId, Object2IntMap<ItemDefinition> itemCounts) {
}
