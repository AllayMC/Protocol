package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to show a certain animation on the screen of the player. The packet is used,
 * as an example, for when a raid is triggered and when a raid is defeated.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class OnScreenTextureAnimationPacket implements BedrockPacket {
    private long effectId;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ON_SCREEN_TEXTURE_ANIMATION;
    }

    @Override
    public OnScreenTextureAnimationPacket clone() {
        try {
            return (OnScreenTextureAnimationPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
