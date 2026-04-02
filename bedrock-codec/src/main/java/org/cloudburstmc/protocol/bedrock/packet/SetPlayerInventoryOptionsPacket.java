package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryLayout;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryTabLeft;
import org.cloudburstmc.protocol.bedrock.data.inventory.InventoryTabRight;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * A bidirectional packet that can be used to update the inventory options of a player.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetPlayerInventoryOptionsPacket implements BedrockPacket {
    /**
     * The tab selected on the left side of the inventory UI, usually in the creative inventory.
     */
    private InventoryTabLeft leftTab;
    /**
     * The tab selected on the right side of the inventory UI, usually in the player's inventory.
     */
    private InventoryTabRight rightTab;
    /**
     * The whether the player has enabled the filtering between recipes they have unlocked or not.
     */
    private boolean filtering;
    /**
     * The layout.
     */
    private InventoryLayout layout;
    /**
     * The layout used for the crafting portion of the inventory UI.
     */
    private InventoryLayout craftingLayout;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_PLAYER_INVENTORY_OPTIONS;
    }

    @Override
    public SetPlayerInventoryOptionsPacket clone() {
        try {
            return (SetPlayerInventoryOptionsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
