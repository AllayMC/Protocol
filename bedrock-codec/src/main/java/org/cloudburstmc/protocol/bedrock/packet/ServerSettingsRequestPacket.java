package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to request the settings specific to the server. These settings are shown in a
 * separate tab client-side, and have the same structure as a custom form.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerSettingsRequestPacket implements BedrockPacket {

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.SERVER_SETTINGS_REQUEST;
  }

  @Override
  public ServerSettingsRequestPacket clone() {
    try {
      return (ServerSettingsRequestPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
