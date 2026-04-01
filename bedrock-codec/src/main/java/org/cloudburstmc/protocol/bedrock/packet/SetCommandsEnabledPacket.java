package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to enable or disable the ability to execute commands for the client. If
 * disabled, the client itself will stop the execution of commands.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetCommandsEnabledPacket implements BedrockPacket {
    private boolean commandsEnabled;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_COMMANDS_ENABLED;
    }

    @Override
    public SetCommandsEnabledPacket clone() {
        try {
            return (SetCommandsEnabledPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
