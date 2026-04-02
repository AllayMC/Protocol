package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Builder;

/**
 * CreativeGroup represents a group of items in the creative inventory. Each group has a category,
 * name and an icon that represents the group.
 *
 * @param category The creative inventory category that the group falls under.
 * @param name     The locale name of the group, i.e. "itemGroup.name.planks".
 * @param icon     The item that represents the group in the creative inventory.
 */
@Builder(toBuilder = true)
public record CreativeItemGroup(CreativeItemCategory category, String name, ItemData icon) {
}
