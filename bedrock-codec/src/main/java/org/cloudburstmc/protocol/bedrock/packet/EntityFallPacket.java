package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client when it falls from a distance onto a block that would damage the player. This
 * packet should not be used at all by the server, as it can easily be spoofed using a proxy or
 * custom client. Servers should implement fall damage using their own calculations.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EntityFallPacket implements BedrockPacket {
    private long runtimeEntityId;
    private float fallDistance;
    private boolean inVoid;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ENTITY_FALL;
    }

    @Override
    public EntityFallPacket clone() {
        try {
            return (EntityFallPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
