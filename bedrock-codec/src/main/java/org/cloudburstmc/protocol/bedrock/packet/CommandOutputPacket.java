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
    private final List<CommandOutputMessage> messages = new ObjectArrayList<>();
    private CommandOriginData commandOriginData;
    private CommandOutputType type;
    private int successCount;
    @Nullable private String data;

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
