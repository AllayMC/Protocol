package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;

/**
 * Sent by the server to close a container the player currently has opened, which was opened using
 * the ContainerOpen packet, or by the client to tell the server it closed a particular container,
 * such as the crafting grid.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ContainerClosePacket implements BedrockPacket {
    /**
     * The window ID of the container being closed.
     */
    private byte id;
    /**
     * Whether the close was initiated by the server rather than the client.
     *
     * @since v419
     */
    private boolean serverInitiated;
    /**
     * The container type used by the client to validate the close request.
     *
     * @since v685
     */
    private ContainerType type;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CONTAINER_CLOSE;
    }

    @Override
    public ContainerClosePacket clone() {
        try {
            return (ContainerClosePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
