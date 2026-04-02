package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * ItemDescriptorCount represents an item descriptor that has a count attached with it, such as a
 * recipe ingredient.
 *
 * @param descriptor Represents how the item is described over the network. It is one of the descriptors above.
 * @param count      The count of items that the item descriptor is required to have.
 */
public record ItemDescriptorWithCount(ItemDescriptor descriptor, int count) {

    public static final ItemDescriptorWithCount EMPTY = new ItemDescriptorWithCount(InvalidDescriptor.INSTANCE, 0);

    public ItemData toItem() {
        if (descriptor == InvalidDescriptor.INSTANCE) {
            return ItemData.AIR;
        }
        return descriptor.toItem()
                .count(count)
                .build();
    }

    public static ItemDescriptorWithCount fromItem(ItemData item) {
        if (item == ItemData.AIR) {
            return EMPTY;
        }
        return new ItemDescriptorWithCount(new DefaultDescriptor(item.getDefinition(), item.getDamage()), item.getCount());
    }
}
