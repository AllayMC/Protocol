package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to damage the player's armor after being hit. The packet should never be used
 * by servers as it hands the responsibility over to the player completely, while the server can
 * easily reliably update the armor damage of players itself.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class HurtArmorPacket implements BedrockPacket {
    /**
     * The amount of damage points that was dealt to the player. The damage to the armour will be
     * calculated by the client based upon this damage, and will also be based upon any enchantments
     * like thorns that the armour may have.
     */
    private int damage;
    /**
     * The damage cause that should be used when applying the armour wear.
     *
     * @since v407
     */
    private int cause;
    /**
     * A bitset of all armour slots affected by this hit.
     *
     * @since v465
     */
    private long armorSlots;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.HURT_ARMOR;
    }

    @Override
    public HurtArmorPacket clone() {
        try {
            return (HurtArmorPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
