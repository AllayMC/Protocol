package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Sent by the server to update a block client-side, without resending the entire chunk that the
 * block is located in. It is particularly useful for small modifications like block
 * breaking/placing.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateBlockPacket implements BedrockPacket {
    public static final Set<Flag> FLAG_ALL =
            Collections.unmodifiableSet(EnumSet.of(Flag.NEIGHBORS, Flag.NETWORK));
    public static final Set<Flag> FLAG_ALL_PRIORITY =
            Collections.unmodifiableSet(EnumSet.of(Flag.NEIGHBORS, Flag.NETWORK, Flag.PRIORITY));

    /**
     * A combination of flags that specify the way the block is updated client-side. It is a
     * combination of the flags below, but typically sending only the network flag is
     * sufficient.
     */
    final Set<Flag> flags = EnumSet.noneOf(Flag.class);
    /**
     * The block position to update.
     */
    Vector3i blockPosition;
    /**
     * The block definition that should appear at {@link #blockPosition} after the update.
     */
    BlockDefinition definition;
    /**
     * The world layer to update. Most blocks use layer {@code 0}, but additional layers are used
     * for blocks that can coexist in the same position.
     */
    int dataLayer;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_BLOCK;
    }

    public enum Flag {
        /**
         * Neighboring blocks should also be updated.
         */
        NEIGHBORS,
        /**
         * The change should be propagated through the normal network block-update path.
         */
        NETWORK,
        /**
         * The client should avoid rendering graphics for this change.
         */
        NO_GRAPHIC,
        /**
         * Reserved flag value with no known current use.
         */
        UNUSED,
        /**
         * Marks this update as high priority.
         */
        PRIORITY
    }

    @Override
    public UpdateBlockPacket clone() {
        try {
            return (UpdateBlockPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
