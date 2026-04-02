package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * InvalidItemDescriptor represents an invalid item descriptor. This is usually sent by the vanilla
 * server for empty slots or ingredients.
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvalidDescriptor implements ItemDescriptor {

    public static final InvalidDescriptor INSTANCE = new InvalidDescriptor();

    @Override
    public ItemDescriptorType getType() {
        return ItemDescriptorType.INVALID;
    }

    @Override
    public ItemData.Builder toItem() {
        throw new UnsupportedOperationException();
    }
}
