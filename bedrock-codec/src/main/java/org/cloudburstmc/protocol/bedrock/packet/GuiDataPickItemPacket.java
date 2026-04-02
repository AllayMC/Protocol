package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make the client 'select' a hot bar slot. It currently appears to be broken
 * however, and does not actually set the selected slot to the hot bar slot set in the packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class GuiDataPickItemPacket implements BedrockPacket {
    /**
     * The item name shown in the top line of the selection popup.
     */
    private String description;
    /**
     * The line under the ItemName, where the effects of the item are usually situated.
     */
    private String itemEffects;
    /**
     * The hotbar slot to select. The client currently appears to ignore this value.
     */
    private int hotbarSlot;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.GUI_DATA_PICK_ITEM;
    }

    @Override
    public GuiDataPickItemPacket clone() {
        try {
            return (GuiDataPickItemPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
