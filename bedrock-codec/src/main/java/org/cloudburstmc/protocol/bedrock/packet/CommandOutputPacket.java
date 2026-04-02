package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOriginData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOutputMessage;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOutputType;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to the client to send text as output of a command. Most servers do not use
 * this packet and instead simply send Text packets, but there is reason to send it. If the origin
 * of a CommandRequest packet is not the player itself, but, for example, a websocket server,
 * sending a Text packet will not do what is expected: The message should go to the websocket
 * server, not to the client's chat. The CommandOutput packet will make sure the messages are
 * relayed to the correct origin of the command request.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CommandOutputPacket implements BedrockPacket {
    /**
     * Output messages produced by the command execution.
     */
    private final List<CommandOutputMessage> messages = new ObjectArrayList<>();
    /**
     * CommandOrigin is the data specifying the origin of the command. In other words, the source
     * that the command request was from, such as the player itself or a websocket server. The
     * client forwards the messages in this packet to the right origin, depending on what is sent
     * here.
     */
    private CommandOriginData commandOriginData;
    /**
     * The output routing mode. Vanilla typically sends {@link CommandOutputType#ALL_OUTPUT}.
     */
    private CommandOutputType type;
    /**
     * The amount of times that a command was executed successfully as a result of the command that
     * was requested. For servers, this is usually a rather meaningless fields, but for vanilla,
     * this is applicable for commands created with Functions.
     */
    private int successCount;
    /**
     * Optional extra dataset payload used by the {@code DATA_SET} output type.
     */
    @Nullable
    private String data;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.COMMAND_OUTPUT;
    }

    @Override
    public CommandOutputPacket clone() {
        try {
            return (CommandOutputPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
