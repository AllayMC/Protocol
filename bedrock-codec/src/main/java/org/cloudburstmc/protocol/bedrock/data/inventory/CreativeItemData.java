package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.Builder;

/**
 * Represents a creative item present in the creative inventory.
 *
 * @param item    The item that should be added to the creative inventory.
 * @param netId   The net ID.
 * @param groupId The group ID.
 */
@Builder(toBuilder = true)
public record CreativeItemData(ItemData item, int netId, int groupId) {
}
