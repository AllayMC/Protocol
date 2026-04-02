package org.cloudburstmc.protocol.bedrock.data;

import lombok.Data;

import java.util.EnumSet;
import java.util.Set;

/**
 * Represents the abilities of a specific layer, such as the base layer or the spectator layer.
 */
@Data
public class AbilityLayer {
    /**
     * The type of ability layer, such as the base, spectator, or editor layer.
     */
    private Type layerType;
    /**
     * The abilities that are enabled in this layer.
     */
    private final Set<Ability> abilitiesSet = EnumSet.noneOf(Ability.class);
    /**
     * The ability values that are actually applied for the enabled abilities in this layer.
     */
    private final Set<Ability> abilityValues = EnumSet.noneOf(Ability.class);
    /**
     * The default horizontal fly speed of the layer.
     */
    private float flySpeed;
    /**
     * The default walk speed of the layer.
     */
    private float walkSpeed;
    /**
     * The default vertical fly speed of the layer.
     *
     * @since v776
     */
    private float verticalFlySpeed;

    public enum Type {
        CACHE,
        BASE,
        SPECTATOR,
        COMMANDS,
        /**
         * @since v557
         */
        EDITOR,
        /**
         * @since v712
         */
        LOADING_SCREEN
    }
}
