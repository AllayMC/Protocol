package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.data.BlockSyncType;

/**
 * Sent by the server to synchronise the falling of a falling block entity with the transitioning
 * back and forth from and to a solid block. It is used to prevent the entity from flickering, and
 * is used in places such as the pushing of blocks with pistons.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = true)
public class UpdateBlockSyncedPacket extends UpdateBlockPacket {
    /**
     * The runtime ID of the falling block entity involved in the transition. For both transition
     * directions this ID should point to the falling block entity, not the solid block.
     */
    private long runtimeEntityId;
    /**
     * The direction of the block/entity transition, such as a block becoming a falling entity or a
     * falling entity settling back into a block.
     */
    private BlockSyncType entityBlockSyncType;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_BLOCK_SYNCED;
    }

    public String toString() {
        return "UpdateBlockSyncedPacket(runtimeEntityId="
               + this.runtimeEntityId
               + ", entityBlockSyncType="
               + this.entityBlockSyncType
               + ", flags="
               + this.flags
               + ", blockPosition="
               + this.blockPosition
               + ", definition="
               + this.definition
               + ", dataLayer="
               + this.dataLayer
               + ")";
    }

    @Override
    public UpdateBlockSyncedPacket clone() {
        return (UpdateBlockSyncedPacket) super.clone();
    }
}
