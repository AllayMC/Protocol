package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.EnchantOptionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Sent by the server to update the enchantment options displayed when the user opens the
 * enchantment table and puts an item in. This packet was added in 1.16 and allows the server to
 * decide on the enchantments that can be selected by the player. The PlayerEnchantOptions packet
 * should be sent once for every slot update of the enchantment table. The vanilla server sends an
 * empty PlayerEnchantOptions packet when the player opens the enchantment table (air is present in
 * the enchantment table slot) and sends the packet with actual enchantments in it when items are
 * put in that can have enchantments.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(doNotUseGetters = true)
public class PlayerEnchantOptionsPacket implements BedrockPacket {
    /**
     * The enchantment-table options that should be shown for the current item.
     */
    private final List<EnchantOptionData> options = new ArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_ENCHANT_OPTIONS;
    }

    @Override
    public PlayerEnchantOptionsPacket clone() {
        try {
            return (PlayerEnchantOptionsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
