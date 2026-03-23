package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Used to make the client connect to a websocket server. This websocket server has the ability to
 * execute commands on the behalf of the client and it can listen for certain events fired by the
 * client.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AutomationClientConnectPacket implements BedrockPacket {
  private String address;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.AUTOMATION_CLIENT_CONNECT;
  }

  @Override
  public AutomationClientConnectPacket clone() {
    try {
      return (AutomationClientConnectPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
