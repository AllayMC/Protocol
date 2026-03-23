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
  private int formId;
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
