package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;
import java.util.Optional;

/**
 * Sent by the server to spawn a particle effect client-side. Unlike other packets that result in
 * the appearing of particles, this packet can show particles that are not hardcoded in the client.
 * They can be added and changed through behavior packs to implement custom particles.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SpawnParticleEffectPacket implements BedrockPacket {
    private int dimensionId;
    /**
     * @since v332
     */
    private long uniqueEntityId = -1;

    private Vector3f position;
    private String identifier;
    /**
     * @since v503
     */
    private Optional<String> molangVariablesJson;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SPAWN_PARTICLE_EFFECT;
    }

    @Override
    public SpawnParticleEffectPacket clone() {
        try {
            return (SpawnParticleEffectPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
