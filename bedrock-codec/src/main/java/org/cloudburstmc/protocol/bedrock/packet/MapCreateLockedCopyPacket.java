package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to create a locked copy of one map into another map. In vanilla, it is used in
 * the cartography table to create a map that is locked and cannot be modified.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MapCreateLockedCopyPacket implements BedrockPacket {
    /**
     * The ID of the map that is being copied. The locked copy will obtain all content that is
     * visible on this map, except the content will not change.
     */
    private long originalMapId;
    /**
     * The ID of the map that will receive the locked copy. Once copied, this map keeps the same
     * contents as the original, but those contents can no longer change.
     */
    private long newMapId;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MAP_CREATE_LOCKED_COPY;
    }

    @Override
    public MapCreateLockedCopyPacket clone() {
        try {
            return (MapCreateLockedCopyPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
