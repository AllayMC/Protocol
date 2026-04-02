package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;

/**
 * Sent by players to send their movement to the server, and by the server to update the movement of
 * player entities to other players.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MovePlayerPacket implements BedrockPacket {
    /**
     * The runtime ID of the player. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * The player's updated position.
     */
    private Vector3f position;
    /**
     * The player's pitch, yaw, and head yaw, in degrees.
     */
    private Vector3f rotation;
    /**
     * The movement mode. This controls how the update should be interpreted by other clients, for
     * example as a normal move, a respawn, or a teleport.
     */
    private Mode mode;
    /**
     * Specifies if the player is considered on the ground. Note that proxies or hacked clients
     * could fake this to always be true, so it should not be taken for granted.
     */
    private boolean onGround;
    /**
     * The runtime ID of the entity the player is currently riding, or {@code 0} if the player is
     * not riding anything.
     */
    private long ridingRuntimeEntityId;
    /**
     * The cause of the teleport, written only when {@link #mode} is {@link Mode#TELEPORT}.
     */
    private TeleportationCause teleportationCause;
    /**
     * The numeric entity type responsible for the teleport, such as an ender pearl projectile.
     */
    private int entityType;
    /**
     * The server tick at which the packet was sent. It is used in relation to
     * CorrectPlayerMovePrediction.
     *
     * @since v419
     */
    private long tick;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOVE_PLAYER;
    }

    public enum Mode {
        NORMAL,
        RESPAWN,
        TELEPORT,
        HEAD_ROTATION
    }

    public enum TeleportationCause {
        UNKNOWN,
        PROJECTILE,
        CHORUS_FRUIT,
        COMMAND,
        BEHAVIOR;

        private static final InternalLogger log =
                InternalLoggerFactory.getInstance(TeleportationCause.class);

        private static final TeleportationCause[] VALUES = values();

        public static TeleportationCause byId(int id) {
            if (id >= 0 && id < VALUES.length) {
                return VALUES[id];
            }
            log.debug("Unknown teleportation cause ID: {}", id);
            return UNKNOWN;
        }
    }

    @Override
    public MovePlayerPacket clone() {
        try {
            return (MovePlayerPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
