package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.ContainerMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.MaterialReducer;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.PotionMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.RecipeData;

import java.util.List;

/**
 * Sent by the server to let the client know all crafting data that the server maintains. This
 * includes shapeless crafting, crafting table recipes, furnace recipes etc. Each crafting station's
 * recipes are included in it.
 */
@Data
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class CraftingDataPacket implements BedrockPacket {
    /**
     * Recipes available on the server, including shaped, shapeless, furnace, and other crafting
     * station recipes.
     */
    private final List<RecipeData> craftingData = new ObjectArrayList<>();
    /**
     * Potion mixing recipes that can be used in a brewing stand.
     */
    private final List<PotionMixData> potionMixData = new ObjectArrayList<>();
    /**
     * Recipes that convert potion containers between forms such as drinkable, splash, and
     * lingering.
     */
    private final List<ContainerMixData> containerMixData = new ObjectArrayList<>();
    /**
     * Whether the client should discard all previously known recipes before applying this packet.
     */
    private boolean cleanRecipes;
    /**
     * A list of all material reducers which is used in education edition chemistry.
     *
     * @since v465
     */
    private final List<MaterialReducer> materialReducers = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CRAFTING_DATA;
    }

    @Override
    public CraftingDataPacket clone() {
        try {
            return (CraftingDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
