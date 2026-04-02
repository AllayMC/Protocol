package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * ComplexAliasItemDescriptor represents an item descriptor that uses a single name to identify the
 * item. There is no clear benefit of using this item descriptor and only seem to be used for
 * specific recipes.
 *
 * @param name The name of the item, which is a name like 'minecraft:stick'.
 */
public record ComplexAliasDescriptor(String name) implements ItemDescriptor {
    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.COMPLEX_ALIAS;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}
