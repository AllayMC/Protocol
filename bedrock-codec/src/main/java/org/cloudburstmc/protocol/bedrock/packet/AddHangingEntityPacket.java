package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make a hanging entity show up client-side.
 *
 * @deprecated Removed in 1.12.0 (v361).
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class AddHangingEntityPacket implements BedrockPacket {
    /**
     * The unique ID of the hanging entity. The unique ID stays stable across sessions of the same
     * world, but servers often reuse the runtime ID here.
     */
    private long uniqueEntityId;
    /**
     * The runtime ID of the hanging entity. The runtime ID is unique for the current session and is
     * the identifier used by most other packets.
     */
    private long runtimeEntityId;
    /**
     * The block position where the hanging entity is attached.
     */
    private Vector3f position;
    /**
     * The horizontal facing of the hanging entity.
     */
    private int direction;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_HANGING_ENTITY;
    }

    @Override
    public AddHangingEntityPacket clone() {
        try {
            return (AddHangingEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
