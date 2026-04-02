package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryTransactionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.ItemUseTransaction;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.LegacySetItemSlotData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * A packet sent by the client. It essentially exists out of multiple sub-packets, each of which
 * have something to do with the inventory in one way or another. Some of these sub-packets directly
 * relate to the inventory, others relate to interaction with the world, that could potentially
 * result in a change in the inventory.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class InventoryTransactionPacket implements BedrockPacket {
    /**
     * Legacy slot updates included when {@link #legacyRequestId} is non-zero. The server should
     * echo these back in an item stack response when the transaction is accepted.
     */
    private final List<LegacySetItemSlotData> legacySlots = new ObjectArrayList<>();
    /**
     * A list of actions that took place, that form the inventory transaction together. Each of
     * these actions hold one slot in which one item was changed to another. In general, the
     * combination of all of these actions results in a balanced inventory transaction. This should
     * be checked to ensure that no items are cheated into the inventory.
     */
    private final List<InventoryActionData> actions = new ObjectArrayList<>();
    /**
     * The type of transaction payload carried by this packet.
     */
    private InventoryTransactionType transactionType;
    /**
     * The action type used by item-use transaction variants.
     */
    private int actionType;
    /**
     * The runtime ID of the entity involved in the transaction.
     */
    private long runtimeEntityId;
    /**
     * The position of the block involved in the transaction.
     */
    private Vector3i blockPosition;
    /**
     * The face of the block that was interacted with.
     */
    private int blockFace;
    /**
     * The selected hotbar slot used for the interaction.
     */
    private int hotbarSlot;
    /**
     * The item held by the client while performing the transaction.
     */
    private ItemData itemInHand;
    /**
     * The player position used for the interaction.
     */
    private Vector3f playerPosition;
    /**
     * The exact interaction position relative to the block.
     */
    private Vector3f clickPosition;
    /**
     * The client-reported head position for the interaction.
     */
    private Vector3f headPosition;
    /**
     * The definition of the block being interacted with. This is only used for item-use
     * transactions.
     *
     * @since v340
     */
    private BlockDefinition blockDefinition;
    /**
     * An ID that is only non-zero at times when sent by the client. The server should always send 0
     * for this. When this field is not 0, the LegacySetItemSlots slice below will have values in
     * it. LegacyRequestID ties in with the ItemStackResponse packet. If this field is non-0, the
     * server should respond with an ItemStackResponse packet. Some inventory actions such as
     * dropping an item out of the hotbar are still one using this packet, and the ItemStackResponse
     * packet needs to tie in with it.
     *
     * @since v407
     */
    private int legacyRequestId;
    /**
     * Whether network IDs were used for this transaction.
     *
     * @since v407
     * @deprecated since v431
     */
    @Deprecated
    private boolean usingNetIds;
    /**
     * The trigger that caused the item-use transaction.
     *
     * @since v712
     */
    private ItemUseTransaction.TriggerType triggerType;
    /**
     * The client's predicted interaction result.
     *
     * @since v712
     */
    private ItemUseTransaction.PredictedResult clientInteractPrediction;
    /**
     * The client's cooldown state for the interacted item.
     *
     * @since v944
     */
    private int clientCooldownState;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.INVENTORY_TRANSACTION;
    }

    @Override
    public InventoryTransactionPacket clone() {
        try {
            return (InventoryTransactionPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
