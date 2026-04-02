package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * ItemTagItemDescriptor represents an item descriptor that uses item tagging. This should be used
 * to reduce duplicative entries for items that can be grouped under a single tag.
 *
 * @param itemTag The item tag.
 */
public record ItemTagDescriptor(String itemTag) implements ItemDescriptor {
    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.ITEM_TAG;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}
