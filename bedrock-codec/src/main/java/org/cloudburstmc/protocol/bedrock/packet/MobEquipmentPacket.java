package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to the server and the server to the client to make the other side aware of the
 * new item that an entity is holding. It is used to show the item in the hand of entities such as
 * zombies too.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MobEquipmentPacket implements BedrockPacket {
    /**
     * The runtime ID of the entity. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * The item the entity should be shown holding after this update.
     */
    private ItemData item;
    /**
     * The slot in the inventory that was held. This is the same as HotBarSlot, and only remains for
     * backwards compatibility.
     */
    private int inventorySlot;
    /**
     * The selected hotbar slot. This mirrors {@link #inventorySlot} in modern protocols, which is
     * kept mostly for backwards compatibility.
     */
    private int hotbarSlot;
    /**
     * The ID of the container whose held item changed, usually the main inventory window.
     */
    private int containerId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOB_EQUIPMENT;
    }

    @Override
    public MobEquipmentPacket clone() {
        try {
            return (MobEquipmentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
