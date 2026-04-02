package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;

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
    /**
     * The dimension in which the particle should be spawned. The client does not appear to apply a
     * strong behavioural difference based on this value, but it is still transmitted by the
     * protocol.
     */
    private int dimensionId;
    /**
     * The position that the particle should be spawned at. If the position is too far away from the
     * player, it will not show up. If EntityUniqueID is not -1, the position will be relative to
     * the position of the entity.
     */
    private Vector3f position;
    /**
     * The particle effect identifier. This may refer to a built-in particle or one supplied
     * through behaviour packs.
     */
    private String identifier;
    /**
     * The unique ID of the entity that the spawned particle may be attached to. If this ID is not
     * -1, the Position below will be interpreted as relative to the position of the entity
     * associated with this unique ID.
     *
     * @since v332
     */
    private long uniqueEntityId = -1;
    /**
     * An optional JSON object containing MoLang variables used when instantiating the particle
     * effect. In many cases this may be left empty.
     *
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
