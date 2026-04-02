package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * Represents a type of item descriptor. This is one of the concrete types below. It is an alias of
 * Marshaler.
 */
public interface ItemDescriptor {

    ItemDescriptorType getType();

    ItemData.Builder toItem();
}
