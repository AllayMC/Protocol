package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * DeferredItemDescriptor represents an item descriptor that uses a namespace and metadata value to
 * identify the item. There is no clear benefit of using this item descriptor.
 *
 * @param fullName The full name.
 * @param auxValue The aux value.
 */
public record DeferredDescriptor(String fullName, int auxValue) implements ItemDescriptor {
    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.DEFERRED;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}
