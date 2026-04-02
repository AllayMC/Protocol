package org.cloudburstmc.protocol.bedrock.data.inventory.transaction;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

import java.util.List;

/**
 * Represents item use transaction used in the Bedrock protocol.
 */
@Data
public class ItemUseTransaction {
    /**
     * The legacy request ID.
     */
    private int legacyRequestId;
    /**
     * The legacy slots.
     */
    private final List<LegacySetItemSlotData> legacySlots = new ObjectArrayList<>();
    /**
     * Whether using net ids.
     */
    private boolean usingNetIds;
    /**
     * The actions.
     */
    private final List<InventoryActionData> actions = new ObjectArrayList<>();
    /**
     * The action type.
     */
    private int actionType;
    /**
     * The block position.
     */
    private Vector3i blockPosition;
    /**
     * The block face.
     */
    private int blockFace;
    /**
     * The hotbar slot.
     */
    private int hotbarSlot;
    /**
     * The item in hand.
     */
    private ItemData itemInHand;
    /**
     * The player position.
     */
    private Vector3f playerPosition;
    /**
     * The click position.
     */
    private Vector3f clickPosition;
    /**
     * The block definition.
     */
    private BlockDefinition blockDefinition;
    /**
     * The client interact prediction.
     *
     * @since v712
     */
    private PredictedResult clientInteractPrediction;
    /**
     * The trigger type.
     *
     * @since v712
     */
    private TriggerType triggerType;
    /**
     * The client cooldown state.
     *
     * @since v944
     */
    private int clientCooldownState;

    public enum PredictedResult {
        FAILURE,
        SUCCESS
    }

    public enum TriggerType {
        UNKNOWN,
        PLAYER_INPUT,
        SIMULATION_TICK
    }
}
