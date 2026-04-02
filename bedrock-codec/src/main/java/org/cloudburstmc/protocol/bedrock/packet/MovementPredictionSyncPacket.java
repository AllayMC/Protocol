package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;

import java.util.Set;

/**
 * Sent by the client to the server periodically if the client has received movement corrections
 * from the server, containing information about client-predictions that are relevant to movement.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MovementPredictionSyncPacket implements BedrockPacket {
    /**
     * The runtime ID of the client player whose prediction state is being synchronised.
     */
    private long runtimeEntityId;
    /**
     * The current set of entity flags known to the client.
     */
    private final Set<EntityFlag> flags = new ObjectOpenHashSet<>();
    /**
     * The width, height, and scale-related bounding box values reported by the client for movement
     * prediction.
     */
    private Vector3f boundingBox;
    /**
     * The movement speed attribute, or {@code 0} if it is not set.
     */
    private float speed;
    /**
     * UnderwaterMovementSpeed is the underwater movement speed attribute or 0 if not set.
     */
    private float underwaterSpeed;
    /**
     * LavaMovementSpeed is the lava movement speed attribute or 0 if not set.
     */
    private float lavaSpeed;
    /**
     * The jump strength attribute or 0 if not set.
     */
    private float jumpStrength;
    /**
     * The health attribute or 0 if not set.
     */
    private float health;
    /**
     * The hunger attribute or 0 if not set.
     */
    private float hunger;
    /**
     * Specifies if the client is currently flying.
     *
     * @since v786
     */
    private boolean flying;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOVEMENT_PREDICTION_SYNC;
    }

    @Override
    public MovementPredictionSyncPacket clone() {
        try {
            return (MovementPredictionSyncPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
