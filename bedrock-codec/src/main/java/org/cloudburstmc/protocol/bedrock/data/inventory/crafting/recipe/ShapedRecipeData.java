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
 * Represents a recipe that has a specific shape that must be used to craft the output of the
 * recipe. Trying to craft the item in any other shape will not work. The ShapedRecipe is of the
 * same structure as the ShapedChemistryRecipe.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ShapedRecipeData implements CraftingRecipeData {

    /**
     * The type.
     */
    private final CraftingDataType type;
    /**
     * The width of the recipe's shape.
     */
    private final int width;
    /**
     * The height of the recipe's shape.
     */
    private final int height;
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
     * Whether assume symetry.
     *
     * @since v671
     */
    private final boolean assumeSymetry;
    /**
     * The requirement.
     *
     * @since v685
     */
    private final RecipeUnlockingRequirement requirement;

    public static ShapedRecipeData of(CraftingDataType type, String id, int width, int height,
                                      List<ItemDescriptorWithCount> ingredients, List<ItemData> results, UUID uuid,
                                      String tag, int priority, int netId) {
        return ShapedRecipeData.of(type, id, width, height, ingredients, results, uuid, tag, priority, netId, false,
                RecipeUnlockingRequirement.INVALID);
    }

    public static ShapedRecipeData of(CraftingDataType type, String id, int width, int height,
                                      List<ItemDescriptorWithCount> ingredients, List<ItemData> results, UUID uuid,
                                      String tag, int priority, int netId, boolean assumeSymetry, RecipeUnlockingRequirement requirement) {
        checkArgument(type == CraftingDataType.SHAPED || type == CraftingDataType.SHAPED_CHEMISTRY,
                "type must be SHAPED or SHAPED_CHEMISTRY");
        return new ShapedRecipeData(type, width, height, ingredients, results, uuid, tag, id, priority, netId, assumeSymetry, requirement);
    }

    public static ShapedRecipeData of(CraftingDataType type, String id, int width, int height,
                                      List<ItemDescriptorWithCount> ingredients, List<ItemData> results, UUID uuid,
                                      String tag, int priority, int netId, boolean assumeSymetry) {
        return ShapedRecipeData.of(type, id, width, height, ingredients, results, uuid, tag, priority, netId, assumeSymetry,
                RecipeUnlockingRequirement.INVALID);
    }

    public static ShapedRecipeData shaped(String id, int width, int height, List<ItemDescriptorWithCount> ingredients,
                                          List<ItemData> results, UUID uuid, String tag, int priority, int netId, boolean assumeSymetry,
                                          RecipeUnlockingRequirement requirement) {
        return of(CraftingDataType.SHAPED, id, width, height, ingredients, results, uuid, tag, priority, netId, assumeSymetry, requirement);
    }

    public static ShapedRecipeData shapedChemistry(String id, int width, int height,
                                                   List<ItemDescriptorWithCount> ingredients, List<ItemData> results,
                                                   UUID uuid, String tag, int priority, int netId, boolean assumeSymetry) {
        return of(CraftingDataType.SHAPED_CHEMISTRY, id, width, height, ingredients, results, uuid, tag, priority, netId,
                assumeSymetry);
    }
}
