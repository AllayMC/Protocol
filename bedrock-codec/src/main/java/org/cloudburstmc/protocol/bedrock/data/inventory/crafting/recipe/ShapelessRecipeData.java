package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import lombok.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.RecipeUnlockingRequirement;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

import java.util.List;
import java.util.UUID;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

/**
 * Represents a recipe that has no particular shape. Its functionality is shared with the
 * RecipeShulkerBox and RecipeShapelessChemistry types.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ShapelessRecipeData implements CraftingRecipeData {

    /**
     * The type.
     */
    private final CraftingDataType type;
    /**
     * The ingredients.
     */
    private final List<ItemDescriptorWithCount> ingredients;
    /**
     * The results.
     */
    private final List<ItemData> results;
    /**
     * A UUID identifying the recipe. Since the CraftingEvent packet no longer exists, this can
     * always be empty.
     */
    private final UUID uuid;
    /**
     * The tag.
     *
     * @since v354
     */
    private final String tag;
    /**
     * The ID.
     *
     * @since v361
     */
    private final String id;
    /**
     * The priority.
     *
     * @since v361
     */
    private final int priority;
    /**
     * The net ID.
     *
     * @since v407
     */
    private final int netId;
    /**
     * The requirement.
     *
     * @since v685
     */
    private final RecipeUnlockingRequirement requirement;

    public static ShapelessRecipeData of(CraftingDataType type, String id, List<ItemDescriptorWithCount> ingredients,
                                         List<ItemData> results, UUID uuid, String tag, int priority, int netId,
                                         RecipeUnlockingRequirement requirement) {
        checkArgument(type == CraftingDataType.SHAPELESS || type == CraftingDataType.SHAPELESS_CHEMISTRY || type == CraftingDataType.SHULKER_BOX,
                "type must be SHAPELESS, SHAPELESS_CHEMISTRY or SHULKER_BOX");
        return new ShapelessRecipeData(type, ingredients, results, uuid, tag, id, priority, netId, requirement);
    }

    public static ShapelessRecipeData of(CraftingDataType type, String id, List<ItemDescriptorWithCount> ingredients,
                                         List<ItemData> results, UUID uuid, String tag, int priority, int netId) {
        return ShapelessRecipeData.of(type, id, ingredients, results, uuid, tag, priority, netId,
                RecipeUnlockingRequirement.INVALID);
    }

    public static ShapelessRecipeData shapeless(String id, List<ItemDescriptorWithCount> ingredients,
                                                List<ItemData> results, UUID uuid, String tag, int priority, int netId,
                                                RecipeUnlockingRequirement requirement) {
        return of(CraftingDataType.SHAPELESS, id, ingredients, results, uuid, tag, priority, netId, requirement);
    }

    public static ShapelessRecipeData shapelessChemistry(String id, List<ItemDescriptorWithCount> ingredients,
                                                         List<ItemData> results, UUID uuid, String tag, int priority,
                                                         int netId) {
        return of(CraftingDataType.SHAPELESS_CHEMISTRY, id, ingredients, results, uuid, tag, priority, netId);
    }

    public static ShapelessRecipeData shulkerBox(String id, List<ItemDescriptorWithCount> ingredients,
                                                 List<ItemData> results, UUID uuid, String tag, int priority, int netId) {
        return of(CraftingDataType.SHULKER_BOX, id, ingredients, results, uuid, tag, priority, netId);
    }
}
