package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * Represents default descriptor used in the Bedrock protocol.
 *
 * @param itemId   The item ID.
 * @param auxValue The aux value.
 */
public record DefaultDescriptor(ItemDefinition itemId, int auxValue) implements ItemDescriptor {
    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.DEFAULT;
    }

    @Override
    public ItemData.Builder toItem() {
        return ItemData.builder()
                .definition(itemId)
                .damage(auxValue);
    }
}
