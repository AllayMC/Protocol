package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ControlScheme;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server upon the client's request or the usage of the vanilla /controlscheme command.
 * It is used to set the control scheme of the client, often used in combination with custom
 * cameras.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundControlSchemeSetPacket implements BedrockPacket {

  private ControlScheme scheme;

  @Override
  public PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  @Override
  public BedrockPacketType getPacketType() {
    return BedrockPacketType.CLIENTBOUND_CONTROL_SCHEME_SET;
  }

  @Override
  public BedrockPacket clone() {
    try {
      return (ClientboundControlSchemeSetPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
