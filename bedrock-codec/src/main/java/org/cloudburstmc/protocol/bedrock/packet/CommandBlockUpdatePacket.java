package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.CommandBlockMode;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to update a command block at a specific position. The command block may be
 * either a physical block or an entity.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CommandBlockUpdatePacket implements BedrockPacket {
    private boolean block;
    private Vector3i blockPosition;
    private CommandBlockMode mode;
    private boolean redstoneMode;
    private boolean conditional;
    private long minecartRuntimeEntityId;
    private String command;
    private String lastOutput;
    private String name;
    private boolean outputTracked;
    /**
     * @since v361
     */
    private long tickDelay;
    /**
     * @since v361
     */
    private boolean executingOnFirstTick;
    /**
     * @since v776
     */
    private String filteredName;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.COMMAND_BLOCK_UPDATE;
    }

    @Override
    public CommandBlockUpdatePacket clone() {
        try {
            return (CommandBlockUpdatePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
