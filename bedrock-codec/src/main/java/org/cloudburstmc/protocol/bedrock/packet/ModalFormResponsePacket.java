package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ModalFormCancelReason;

import java.util.Optional;

/**
 * Sent by the client in response to a ModalFormRequest, after the player has submitted the form
 * sent. It contains the options/properties selected by the player, or a JSON encoded 'null' if the
 * form was closed by clicking the X at the top right corner of the form.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ModalFormResponsePacket implements BedrockPacket {
    /**
     * The form ID of the form the client has responded to. It is the same as the ID sent in the
     * ModalFormRequest, and may be used to identify which form was submitted.
     */
    private int formId;
    /**
     * The JSON-encoded response payload. This is typically a boolean for modal forms, a button
     * index for simple forms, an array of element values for custom forms, or JSON {@code null} if
     * the form was closed without submitting.
     */
    private String formData;
    /**
     * The reason the form was cancelled instead of submitted, if the client provided one.
     *
     * @since v544
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<ModalFormCancelReason> cancelReason;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MODAL_FORM_RESPONSE;
    }

    @Override
    public ModalFormResponsePacket clone() {
        try {
            return (ModalFormResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
