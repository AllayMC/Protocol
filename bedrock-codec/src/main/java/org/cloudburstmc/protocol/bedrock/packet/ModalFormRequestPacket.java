package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make the client open a form. This form may be either a modal form which has
 * two options, a menu form for a selection of options and a custom form for properties.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ModalFormRequestPacket implements BedrockPacket {
  private int formId;
  private String formData;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.MODAL_FORM_REQUEST;
  }

  @Override
  public ModalFormRequestPacket clone() {
    try {
      return (ModalFormRequestPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
