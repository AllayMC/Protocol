package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.PredictionType;

/**
 * Sent to the client when the server's movement prediction system does not match what the client is
 * sending.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CorrectPlayerMovePredictionPacket implements BedrockPacket {
    /**
     * The position that the player should be at for the corrected tick. The client reapplies later
     * movement starting from this position.
     */
    private Vector3f position;
    /**
     * The difference between the position reported by the client and the server-authoritative
     * position for the corrected tick.
     */
    private Vector3f delta;
    /**
     * Whether the player was on the ground at the corrected tick.
     */
    private boolean onGround;
    /**
     * The movement tick being corrected by the server.
     */
    private long tick;
    /**
     * The type of prediction that is being corrected.
     *
     * @since v649
     */
    private PredictionType predictionType = PredictionType.PLAYER;
    /**
     * The player or vehicle rotation at the corrected tick.
     *
     * @since v671
     */
    private Vector2f vehicleRotation;
    /**
     * The angular velocity of the vehicle that the rider is riding.
     *
     * @since v712
     */
    private Float vehicleAngularVelocity;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CORRECT_PLAYER_MOVE_PREDICTION;
    }

    @Override
    public CorrectPlayerMovePredictionPacket clone() {
        try {
            return (CorrectPlayerMovePredictionPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
