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
    /**
     * Specifies if the command block updated was an actual physical block. If false, the command
     * block is in a minecart and has an entity runtime ID instead.
     */
    private boolean block;
    /**
     * The position of the command block when {@link #block} is {@code true}.
     */
    private Vector3i blockPosition;
    /**
     * The command block mode when {@link #block} is {@code true}.
     */
    private CommandBlockMode mode;
    /**
     * Whether the command block requires redstone power when {@link #block} is {@code true}.
     */
    private boolean redstoneMode;
    /**
     * Specifies the behavior of the command block if the command block before it, on the side
     * opposite the arrow direction, fails to execute. If set to {@code false}, it activates
     * regardless; if set to {@code true}, it activates only when the previous command block
     * executed successfully. Only used when {@link #block} is {@code true}.
     */
    private boolean conditional;
    /**
     * The runtime ID of the minecart carrying the command block when {@link #block} is
     * {@code false}.
     */
    private long minecartRuntimeEntityId;
    /**
     * The command currently entered in the command block. This is the command that is executed when
     * the command block is activated.
     */
    private String command;
    /**
     * The output of the last command executed by the command block.
     */
    private String lastOutput;
    /**
     * The name of the command block updated. If not empty, it will show this name hovering above
     * the command block when hovering over the block with the cursor.
     */
    private String name;
    /**
     * Whether the command block tracks and displays its output.
     */
    private boolean outputTracked;
    /**
     * The delay in ticks between executions of a command block, if it is a repeating command block.
     *
     * @since v361
     */
    private long tickDelay;
    /**
     * Whether the command block should execute on the first tick after being enabled.
     *
     * @since v361
     */
    private boolean executingOnFirstTick;
    /**
     * A profanity-filtered version of {@link #name}. The client uses this when its profanity
     * filter is enabled and the value is not empty.
     *
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
