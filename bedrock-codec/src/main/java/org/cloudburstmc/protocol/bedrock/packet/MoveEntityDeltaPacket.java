package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.EnumSet;
import java.util.Set;

/**
 * Sent by the server to move an entity. The packet is specifically optimised to save as much space
 * as possible, by only writing non-zero fields. As of 1.16.100, this packet no longer actually
 * contains any deltas.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
public class MoveEntityDeltaPacket implements BedrockPacket {
    /**
     * The runtime ID of the entity that is being moved. The packet works provided a non-player
     * entity with this runtime ID is present.
     */
    private long runtimeEntityId;

    /**
     * Flags that indicate which position or rotation components are present in this packet and
     * whether special movement states such as teleporting apply.
     */
    private final Set<Flag> flags = EnumSet.noneOf(Flag.class);

    /**
     * The new absolute X position, if {@link Flag#HAS_X} is present.
     */
    private int deltaX;
    /**
     * The new absolute Y position, if {@link Flag#HAS_Y} is present.
     */
    private int deltaY;
    /**
     * The new absolute Z position, if {@link Flag#HAS_Z} is present.
     */
    private int deltaZ;
    /**
     * The new pitch, if {@link Flag#HAS_PITCH} is present.
     */
    private float pitch;
    /**
     * The new yaw, if {@link Flag#HAS_YAW} is present.
     */
    private float yaw;
    /**
     * The new head yaw, if {@link Flag#HAS_HEAD_YAW} is present.
     */
    private float headYaw;
    /**
     * The new absolute X position for newer protocol versions.
     *
     * @since v419
     */
    private float x;
    /**
     * The new absolute Y position for newer protocol versions.
     *
     * @since v419
     */
    private float y;
    /**
     * The new absolute Z position for newer protocol versions.
     *
     * @since v419
     */
    private float z;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOVE_ENTITY_DELTA;
    }

    public String toString() {
        return "MoveEntityDeltaPacket(runtimeEntityId="
               + runtimeEntityId
               + ", flags="
               + flags
               + ", delta=("
               + deltaX
               + ", "
               + deltaY
               + ", "
               + deltaZ
               + "), position=("
               + x
               + ", "
               + y
               + ", "
               + z
               + "), rotation=("
               + pitch
               + ", "
               + yaw
               + ", "
               + headYaw
               + "))";
    }

    public enum Flag {
        /**
         * The X component is present.
         */
        HAS_X,
        /**
         * The Y component is present.
         */
        HAS_Y,
        /**
         * The Z component is present.
         */
        HAS_Z,
        /**
         * The pitch component is present.
         */
        HAS_PITCH,
        /**
         * The yaw component is present.
         */
        HAS_YAW,
        /**
         * The head yaw component is present.
         */
        HAS_HEAD_YAW,
        /**
         * The entity should be considered on the ground after the move.
         */
        ON_GROUND,
        /**
         * The movement should be treated as a teleport.
         */
        TELEPORTING,
        /**
         * The local entity should be force-moved even if client prediction disagrees.
         */
        FORCE_MOVE_LOCAL_ENTITY
    }

    @Override
    public MoveEntityDeltaPacket clone() {
        try {
            return (MoveEntityDeltaPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
