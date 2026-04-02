package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import lombok.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;

import java.util.UUID;

/**
 * MultiRecipe serves as an 'enable' switch for multi-shape recipes.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiRecipeData implements UniqueCraftingData {

    /**
     * A UUID identifying the recipe. Since the CraftingEvent packet no longer exists, this can
     * always be empty.
     */
    private final UUID uuid;
    /**
     * The net ID.
     *
     * @since v407
     */
    private final int netId;

    @Override
    public CraftingDataType getType() {
        return CraftingDataType.MULTI;
    }

    public static MultiRecipeData of(UUID uuid, int netId) {
        return new MultiRecipeData(uuid, netId);
    }
}
