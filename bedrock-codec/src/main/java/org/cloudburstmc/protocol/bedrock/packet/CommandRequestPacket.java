package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOriginData;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to request the execution of a server-side command. Although some servers
 * support sending commands using the Text packet, this packet is guaranteed to have the correct
 * result.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CommandRequestPacket implements BedrockPacket {
    /**
     * The raw command line entered by the client. The client does not parse it locally.
     */
    private String command;
    /**
     * CommandOrigin is the data specifying the origin of the command. In other words, the source
     * that the command was from, such as the player itself or a websocket server.
     */
    private CommandOriginData commandOriginData;
    /**
     * Specifies whether the request is internal. Setting it to {@code false} appears to work, but
     * the protocol purpose of this flag is still unclear.
     */
    private boolean internal;
    /**
     * The version of the command that is being executed. This field currently has no purpose or
     * functionality.
     *
     * @since v567
     */
    private int version;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.COMMAND_REQUEST;
    }

    @Override
    public CommandRequestPacket clone() {
        try {
            return (CommandRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
