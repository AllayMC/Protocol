package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import lombok.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

/**
 * Represents a recipe specifically used for smithing tables. It has three input items and adds them
 * together, resulting in a new item.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SmithingTransformRecipeData implements TaggedCraftingData, IdentifiableRecipeData, NetworkRecipeData {

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
     * The resulting item from the two items being added together.
     */
    private final ItemData result;
    /**
     * The tag.
     */
    private final String tag;
    /**
     * The net ID.
     */
    private final int netId;
    /**
     * The item that is used to shape the Base item based on the Addition being applied.
     *
     * @since v582
     */
    private final ItemDescriptorWithCount template;

    @Override
    public CraftingDataType getType() {
        return CraftingDataType.SMITHING_TRANSFORM;
    }

    public static SmithingTransformRecipeData of(String id, ItemDescriptorWithCount template, ItemDescriptorWithCount base,
                                                 ItemDescriptorWithCount addition, ItemData result, String tag, int netId) {
        return new SmithingTransformRecipeData(id, base, addition, result, tag, netId, template);
    }

    /**
     * Pre-1.19.80
     */
    public static SmithingTransformRecipeData of(String id, ItemDescriptorWithCount base,
                                                 ItemDescriptorWithCount addition, ItemData result, String tag, int netId) {
        return new SmithingTransformRecipeData(id, base, addition, result, tag, netId, null);
    }
}
