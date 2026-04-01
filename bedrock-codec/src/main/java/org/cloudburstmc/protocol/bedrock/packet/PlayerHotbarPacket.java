package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client. It used to be used to link hot bar slots of the player to
 * actual slots in the inventory, but as of 1.2, this was changed and hot bar slots are no longer a
 * free floating part of the inventory. Since 1.2, the packet has been re-purposed, but its new
 * functionality is not clear.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerHotbarPacket implements BedrockPacket {
    private int selectedHotbarSlot;
    private int containerId;
    private boolean selectHotbarSlot;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_HOTBAR;
    }

    @Override
    public PlayerHotbarPacket clone() {
        try {
            return (PlayerHotbarPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
