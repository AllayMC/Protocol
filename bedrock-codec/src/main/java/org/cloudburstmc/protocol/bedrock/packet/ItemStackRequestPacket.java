package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.common.PacketSignal;
import java.util.ArrayList;
import java.util.List;

/**
 * Sent by the client to change item stacks in an inventory. It is essentially a replacement of the
 * InventoryTransaction packet added in 1.16 for inventory specific actions, such as moving items
 * around or crafting. The InventoryTransaction packet is still used for actions such as placing
 * blocks and interacting with entities.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(doNotUseGetters = true)
public class ItemStackRequestPacket implements BedrockPacket {
    private final List<ItemStackRequest> requests = new ArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ITEM_STACK_REQUEST;
    }

    @Override
    public ItemStackRequestPacket clone() {
        try {
            return (ItemStackRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
