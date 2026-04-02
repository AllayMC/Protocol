package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the client to the server when it jumps while riding an entity that has the WASDControlled
 * entity flag set, for example when riding a horse.
 *
 * @deprecated Removed as of v800 (1.21.80). Server authoritative jump is handled by {@link
 * PlayerAuthInputPacket}
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class RiderJumpPacket implements BedrockPacket {
    /**
     * The jump charge strength that the player released for the ridden mount.
     */
    private int jumpStrength;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RIDER_JUMP;
    }

    @Override
    public RiderJumpPacket clone() {
        try {
            return (RiderJumpPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
