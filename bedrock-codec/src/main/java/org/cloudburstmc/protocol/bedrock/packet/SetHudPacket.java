package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.HudElement;
import org.cloudburstmc.protocol.bedrock.data.HudVisibility;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.Set;

/**
 * Sent by the server to set the visibility of individual HUD elements on the client.
 *
 * @since v649
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetHudPacket implements BedrockPacket {
    /**
     * The HUD elements affected by this update.
     */
    private final Set<HudElement> elements = new ObjectOpenHashSet<>();
    /**
     * The visibility state to apply to the selected HUD elements.
     */
    private HudVisibility visibility;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_HUD;
    }

    @Override
    public SetHudPacket clone() {
        try {
            return (SetHudPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
