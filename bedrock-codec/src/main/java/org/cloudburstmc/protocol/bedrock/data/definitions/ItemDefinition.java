package org.cloudburstmc.protocol.bedrock.data.definitions;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemVersion;
import org.cloudburstmc.protocol.common.NamedDefinition;

/**
 * Represents item definition used in the Bedrock protocol.
 */
public interface ItemDefinition extends NamedDefinition {
    ItemDefinition AIR = new SimpleItemDefinition("minecraft:air", 0, false);
    ItemDefinition LEGACY_FIREWORK = new SimpleItemDefinition("minecraft:fireworks_rocket", 401, false);

    /**
     * Whether this item is data driven.
     *
     * @return true if this item is data driven, false if it is not.
     */
    boolean componentBased();

    /**
     * Get the component data NBT for this item.
     *
     * @return the component data NBT, or null if this item is not data driven.
     */
    default NbtMap componentData() {
        return null;
    }

    default ItemVersion version() {
        return ItemVersion.LEGACY;
    }
}
