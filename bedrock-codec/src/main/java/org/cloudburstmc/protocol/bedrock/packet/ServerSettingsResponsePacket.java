package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Optionally sent by the server in response to a ServerSettingsRequest from the client. It is
 * structured the same as a ModalFormRequest packet, and if filled out correctly, will show a
 * specific tab for the server in the settings of the client. A ModalFormResponse packet is sent by
 * the client in response to a ServerSettingsResponse, when the client fills out the settings and
 * closes the settings again.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerSettingsResponsePacket implements BedrockPacket {
    /**
     * An ID used to identify the form. The ID is saved by the client and sent back when the player
     * submits the form, so that the server can identify which form was submitted.
     */
    private int formId;
    /**
     * A JSON-encoded custom-form definition. The payload has the same structure as a
     * {@link ModalFormRequestPacket} body and describes the dedicated server-settings tab to show
     * to the client.
     */
    private String formData;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVER_SETTINGS_RESPONSE;
    }

    @Override
    public ServerSettingsResponsePacket clone() {
        try {
            return (ServerSettingsResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
