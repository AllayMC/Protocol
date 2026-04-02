package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * MineBlockStackRequestActionData is sent by the client when it breaks a block.
 *
 * @param hotbarSlot          The hotbar slot.
 * @param predictedDurability The predicted durability.
 * @param stackNetworkId      The stack network ID.
 */
public record MineBlockAction(int hotbarSlot, int predictedDurability, int stackNetworkId) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.MINE_BLOCK;
    }

}
