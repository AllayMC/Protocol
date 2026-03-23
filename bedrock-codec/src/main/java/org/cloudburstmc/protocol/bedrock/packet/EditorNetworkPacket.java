package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * A packet sent from the server to the client and vise-versa to communicate editor-mode related
 * information. It carries a single compound tag containing the relevant information.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class EditorNetworkPacket implements BedrockPacket {
  private Object payload; // NBT like

  /**
   * @since v712
   */
  private boolean routeToManager;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.EDITOR_NETWORK;
  }

  @Override
  public EditorNetworkPacket clone() {
    try {
      return (EditorNetworkPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
