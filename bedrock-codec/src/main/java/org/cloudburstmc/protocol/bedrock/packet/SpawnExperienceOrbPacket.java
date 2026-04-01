package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to spawn an experience orb entity client-side. Much like the AddPainting
 * packet, it is one of the few packets that spawn an entity without using the {@link
 * AddEntityPacket}.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SpawnExperienceOrbPacket implements BedrockPacket {
    private Vector3f position;
    private int amount;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SPAWN_EXPERIENCE_ORB;
    }

    @Override
    public SpawnExperienceOrbPacket clone() {
        try {
            return (SpawnExperienceOrbPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
