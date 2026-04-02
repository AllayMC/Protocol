package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.command.CommandData;

import java.util.List;

/**
 * Sent by the server to send the full list of commands that the player is able to use on the
 * server. It includes the argument data needed for client-side auto-completion and usage display.
 * AvailableCommands packets may be resent, but they are typically large and should not be sent
 * more often than necessary.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AvailableCommandsPacket implements BedrockPacket {
    /**
     * The full command list that the client should expose client-side. Sending this packet replaces
     * the commands from any previous {@link AvailableCommandsPacket}; it does not append to them.
     */
    private final List<CommandData> commands = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.AVAILABLE_COMMANDS;
    }

    @Override
    public AvailableCommandsPacket clone() {
        try {
            return (AvailableCommandsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
