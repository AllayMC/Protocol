package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;

/**
 * Sent by the server to move an entity to an absolute position. It is typically used for movements
 * where high accuracy isn't needed, such as for long range teleporting.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MoveEntityAbsolutePacket implements BedrockPacket {
    /**
     * The runtime ID of the entity. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * The position to spawn the entity on. If the entity is on a distance that the player cannot
     * see it, the entity will still show up if the player moves closer.
     */
    private Vector3f position;
    /**
     * A Vec3 holding the X, Y and Z rotation of the entity after the movement. This is a Vec3 for
     * the reason that projectiles like arrows don't have yaw/pitch, but do have roll.
     */
    private Vector3f rotation;
    /**
     * Specifies if the entity should be considered on the ground after the move.
     */
    private boolean onGround;
    /**
     * Specifies if the movement should be treated as a teleport rather than a regular move.
     */
    private boolean teleported;
    /**
     * Forces the local client entity to accept the move even if local prediction disagrees.
     */
    private boolean forceMove;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOVE_ENTITY_ABSOLUTE;
    }

    @Override
    public MoveEntityAbsolutePacket clone() {
        try {
            return (MoveEntityAbsolutePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
