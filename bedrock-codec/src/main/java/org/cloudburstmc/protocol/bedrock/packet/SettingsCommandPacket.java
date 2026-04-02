package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the client when it changes a setting in the settings that results in the issuing of a
 * command to the server, such as when Show Coordinates is enabled.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SettingsCommandPacket implements BedrockPacket {
    /**
     * The full command line issued as a result of the client setting that was changed.
     */
    private String command;
    /**
     * Specifies if the client wants the server to suppress the resulting command output. This is
     * typically true because the setting change already gives the player visual feedback.
     */
    private boolean suppressingOutput;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SETTINGS_COMMAND;
    }

    @Override
    public SettingsCommandPacket clone() {
        try {
            return (SettingsCommandPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
