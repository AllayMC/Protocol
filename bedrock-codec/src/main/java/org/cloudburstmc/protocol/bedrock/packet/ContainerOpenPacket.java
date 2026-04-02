package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to open a container client-side. This container must be physically present in
 * the world, for the packet to have any effect. Unlike Java Edition, Bedrock Edition requires that
 * chests for example must be present and in range to open its inventory.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ContainerOpenPacket implements BedrockPacket {
    /**
     * The window id for the container that is being opened. It may be reused later in
     * {@link ContainerClosePacket}.
     */
    private byte id;
    /**
     * The type of container being opened, such as a chest, hopper, or horse inventory.
     */
    private ContainerType type;
    /**
     * The position of the container block. The client expects this to point at an actual container
     * block unless a valid entity container id is also provided.
     */
    private Vector3i blockPosition;
    /**
     * ContainerEntityUniqueID is the unique ID of the entity container that was opened. It is only
     * used if the ContainerType is one that points to an entity, for example a horse.
     */
    private long uniqueEntityId = -1;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CONTAINER_OPEN;
    }

    @Override
    public ContainerOpenPacket clone() {
        try {
            return (ContainerOpenPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
