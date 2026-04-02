package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import lombok.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

/**
 * Represents a recipe specifically used for applying armour trims to an armour piece inside a
 * smithing table.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SmithingTrimRecipeData implements TaggedCraftingData, IdentifiableRecipeData, NetworkRecipeData {

    /**
     * The ID.
     */
    private final String id;
    /**
     * The item that the Addition is being applied to in the smithing table.
     */
    private final ItemDescriptorWithCount base;
    /**
     * The item that is being added to the Base item to result in a modified item.
     */
    private final ItemDescriptorWithCount addition;
    /**
     * The item that is used to shape the Base item based on the Addition being applied.
     */
    private final ItemDescriptorWithCount template;
    /**
     * The tag.
     */
    private final String tag;
    /**
     * The net ID.
     */
    private final int netId;

    @Override
    public CraftingDataType getType() {
        return CraftingDataType.SMITHING_TRIM;
    }

    public static SmithingTrimRecipeData of(String id, ItemDescriptorWithCount base, ItemDescriptorWithCount addition,
                                            ItemDescriptorWithCount template, String tag, int netId) {
        return new SmithingTrimRecipeData(id, base, addition, template, tag, netId);
    }
}
