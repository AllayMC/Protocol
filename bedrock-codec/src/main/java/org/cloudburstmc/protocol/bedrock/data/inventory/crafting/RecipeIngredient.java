package org.cloudburstmc.protocol.bedrock.data.inventory.crafting;

/**
 * Represents recipe ingredient used in the Bedrock protocol.
 *
 * @param id        The ID.
 * @param auxValue  The aux value.
 * @param stackSize The stack size.
 */
public record RecipeIngredient(int id, int auxValue, int stackSize) {
    public static final RecipeIngredient EMPTY = new RecipeIngredient(0, 0, 0);

    public static RecipeIngredient of(int id, int auxValue, int stackSize) {
        if (id == 0) {
            return EMPTY;
        }
        return new RecipeIngredient(id, auxValue, stackSize);
    }

}
