package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to remove a previously spawned volume entity from the client.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class RemoveVolumeEntityPacket implements BedrockPacket {
    /**
     * The runtime ID of the volume entity to remove.
     */
    private int id;
    /**
     * The dimension that currently contains the volume entity.
     *
     * @since v503
     */
    private int dimension;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REMOVE_VOLUME_ENTITY;
    }

    @Override
    public RemoveVolumeEntityPacket clone() {
        try {
            return (RemoveVolumeEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
