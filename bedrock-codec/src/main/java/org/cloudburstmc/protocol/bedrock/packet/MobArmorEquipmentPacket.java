package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

/**
 * Sent by the server to update the armor worn by an entity. This packet is used for players and
 * other living entities, such as zombies.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MobArmorEquipmentPacket implements BedrockPacket {
    /**
     * The runtime ID of the entity whose armor should be updated. Runtime IDs are scoped to the
     * current world session.
     */
    private long runtimeEntityId;
    /**
     * The item shown in the head armor slot. Items that cannot be equipped on the head are not
     * rendered by the client.
     */
    private ItemData helmet;
    /**
     * The item shown in the chest armor slot. Items that cannot be equipped in this slot are not
     * rendered by the client.
     */
    private ItemData chestplate;
    /**
     * The item shown in the leg armor slot. Items that cannot be equipped in this slot are not
     * rendered by the client.
     */
    private ItemData leggings;
    /**
     * The item shown in the feet armor slot. Items that cannot be equipped in this slot are not
     * rendered by the client.
     */
    private ItemData boots;
    /**
     * The item shown in the body slot. Items that cannot be equipped in this slot are not rendered
     * by the client.
     *
     * @since v712
     */
    private ItemData body;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOB_ARMOR_EQUIPMENT;
    }

    @Override
    public MobArmorEquipmentPacket clone() {
        try {
            return (MobArmorEquipmentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
