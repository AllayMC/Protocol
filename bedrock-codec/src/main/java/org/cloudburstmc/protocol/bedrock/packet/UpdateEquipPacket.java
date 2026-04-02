package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client upon opening a horse inventory. It is used to set the content of
 * the inventory and specify additional properties, such as the items that are allowed to be put in
 * slots of the inventory.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateEquipPacket implements BedrockPacket {
    /**
     * The identifier associated with the window that the UpdateEquip packet concerns. It is the ID
     * sent for the horse inventory that was opened before this packet was sent.
     */
    private short windowId;
    /**
     * The type of the window that was opened. Generally, this is the type of a horse inventory, as
     * the packet is specifically made for that.
     */
    private short windowType;
    /**
     * The size of the horse inventory UI that should be displayed.
     */
    private int size;
    /**
     * The unique ID of the entity whose equipment was 'updated' to the player. It is typically the
     * horse entity that had its inventory opened.
     */
    private long uniqueEntityId;
    /**
     * A network NBT compound containing the equipment inventory contents and slot restrictions.
     */
    private NbtMap tag;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_EQUIP;
    }

    @Override
    public UpdateEquipPacket clone() {
        try {
            return (UpdateEquipPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
