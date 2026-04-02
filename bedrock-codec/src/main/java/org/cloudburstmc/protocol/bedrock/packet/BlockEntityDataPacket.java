package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtMap;

/**
 * Sent by the server to replace the client-side NBT state of a block entity, such as a chest or
 * sign.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class BlockEntityDataPacket implements BedrockPacket {
    /**
     * Position of the block that owns the block entity being updated.
     */
    private Vector3i blockPosition;
    /**
     * Complete block entity NBT that should be applied at {@link #blockPosition}.
     */
    private NbtMap data;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BLOCK_ENTITY_DATA;
    }

    @Override
    public BlockEntityDataPacket clone() {
        try {
            return (BlockEntityDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
