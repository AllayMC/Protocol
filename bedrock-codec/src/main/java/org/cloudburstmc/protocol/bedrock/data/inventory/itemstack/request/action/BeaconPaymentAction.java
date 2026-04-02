package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

/**
 * BeaconPaymentStackRequestActionData is sent by the client when it submits an item to enable effects from a
 * beacon. These items will have been moved into the beacon item slot in advance.
 *
 * @param primaryEffect   The primary effect.
 * @param secondaryEffect The secondary effect.
 */
public record BeaconPaymentAction(int primaryEffect, int secondaryEffect) implements ItemStackRequestAction {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.BEACON_PAYMENT;
    }
}
