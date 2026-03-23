package org.cloudburstmc.protocol.bedrock.packet;

import java.util.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ModalFormCancelReason;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client in response to a ModalFormRequest, after the player has submitted the form
 * sent. It contains the options/properties selected by the player, or a JSON encoded 'null' if the
 * form was closed by clicking the X at the top right corner of the form.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ModalFormResponsePacket implements BedrockPacket {
    private int formId;
    private String formData;

    /**
     * The reason for why the form response was cancelled.
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
