package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to control the stack of fog effects rendered by the client. The fog
 * identifiers themselves are defined by resource packs.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayerFogPacket implements BedrockPacket {
    /**
     * The ordered stack of fog identifiers to render, such as {@code minecraft:fog_ocean}.
     */
    private final List<String> fogStack = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAYER_FOG;
    }

    @Override
    public PlayerFogPacket clone() {
        try {
            return (PlayerFogPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
