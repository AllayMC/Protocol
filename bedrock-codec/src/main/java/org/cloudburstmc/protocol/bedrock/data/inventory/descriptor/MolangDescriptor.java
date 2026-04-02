package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * Represents molang descriptor used in the Bedrock protocol.
 *
 * @param tagExpression The tag expression.
 * @param molangVersion The molang version.
 */
public record MolangDescriptor(String tagExpression, int molangVersion) implements ItemDescriptor {
    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.MOLANG;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}
