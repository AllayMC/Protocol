package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ItemEntry holds information on what item stack should be present in a specific slot.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemStackResponseSlot {
    /**
     * The slot.
     */
    private int slot;
    /**
     * The hotbar slot.
     */
    private int hotbarSlot;
    /**
     * The count.
     */
    private int count;

    /**
     * stackNetworkID is the network ID of the new stack at a specific slot.
     */
    private int stackNetworkId;

    /**
     * Holds the final custom name of a renamed item, if relevant.
     *
     * @since v422
     */
    private String customName;

    /**
     * The durability correction.
     *
     * @since v428
     */
    private int durabilityCorrection;
    /**
     * The filtered custom name. Since v2168 the wire field is optional: null means
     * absent, while an empty string is a present name. Earlier codecs require a string.
     *
     * @since v766
     */
    private String filteredCustomName = "";
}
