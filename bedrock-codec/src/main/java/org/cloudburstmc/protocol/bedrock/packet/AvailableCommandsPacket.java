package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.command.CommandData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to send a list of all commands that the player is able to use on the server.
 * This packet holds all the arguments of each commands as well, making it possible for the client
 * to provide auto-completion and command usages. AvailableCommands packets can be resent, but the
 * packet is often very big, so doing this very often should be avoided.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AvailableCommandsPacket implements BedrockPacket {
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
