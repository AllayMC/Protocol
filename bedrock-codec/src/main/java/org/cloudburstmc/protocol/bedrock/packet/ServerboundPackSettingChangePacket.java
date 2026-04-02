package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.UUID;

/**
 * Sent by the client to the server when it changes a setting for a specific pack in the pack
 * settings UI.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundPackSettingChangePacket implements BedrockPacket {

    /**
     * The unique ID of the pack whose setting changed.
     */
    private UUID packId;
    /**
     * The name of the setting that was changed.
     */
    private String packSettingName;
    /**
     * The new value applied to {@link #packSettingName}.
     */
    private Object packSettingValue;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_PACK_SETTING_CHANGE;
    }

    @Override
    public ServerboundPackSettingChangePacket clone() {
        try {
            return (ServerboundPackSettingChangePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
