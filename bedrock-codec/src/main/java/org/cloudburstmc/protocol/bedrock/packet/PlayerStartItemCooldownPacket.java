package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client to start the cooldown for an item category.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerStartItemCooldownPacket implements BedrockPacket {

    /**
     * The item cooldown category identifier.
     */
    private String itemCategory;
    /**
     * The cooldown duration in ticks.
     */
    private int cooldownDuration;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_START_ITEM_COOLDOWN;
    }

    @Override
    public PlayerStartItemCooldownPacket clone() {
        try {
            return (PlayerStartItemCooldownPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
