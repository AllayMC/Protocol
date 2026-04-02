package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;

/**
 * Sent by the server to seed the client's predictive movement history for an entity. Vanilla uses
 * this packet instead of {@link SetEntityMotionPacket} when spatial optimisations are enabled.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MotionPredictionHintsPacket implements BedrockPacket {
    /**
     * The runtime ID of the entity whose velocity is being hinted to the client.
     */
    private long runtimeEntityId;
    /**
     * The server-calculated velocity of the entity at the time the packet was sent.
     */
    private Vector3f motion;
    /**
     * Whether the server currently considers the entity to be on the ground.
     */
    private boolean onGround;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_ENTITY_MOTION_PLUS;
    }

    @Override
    public MotionPredictionHintsPacket clone() {
        try {
            return (MotionPredictionHintsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
