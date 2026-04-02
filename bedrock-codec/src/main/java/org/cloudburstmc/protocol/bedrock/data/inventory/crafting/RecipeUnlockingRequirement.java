package org.cloudburstmc.protocol.bedrock.data.inventory.crafting;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

import java.util.List;

/**
 * RecipeUnlockRequirement represents a requirement that must be met in order to unlock a recipe.
 * This is used for both shaped and shapeless recipes.
 *
 * @param context     The context in which the recipe is unlocked.
 * @param ingredients Ingredients are the ingredients required to unlock the recipe and only used
 *                    if Context is set to none.
 */
public record RecipeUnlockingRequirement(UnlockingContext context, List<ItemDescriptorWithCount> ingredients) {
    public static final RecipeUnlockingRequirement INVALID = new RecipeUnlockingRequirement(UnlockingContext.NONE);

    public RecipeUnlockingRequirement(UnlockingContext context) {
        this(context, new ObjectArrayList<>());
    }

    public boolean isInvalid() {
        return ingredients.isEmpty() && context == UnlockingContext.NONE;
    }

    public enum UnlockingContext {
        NONE,
        ALWAYS_UNLOCKED,
        PLAYER_IN_WATER,
        PLAYER_HAS_MANY_ITEMS;

        private static final UnlockingContext[] VALUES = values();

        public static UnlockingContext from(int id) {
            return VALUES[id];
        }
    }
}
