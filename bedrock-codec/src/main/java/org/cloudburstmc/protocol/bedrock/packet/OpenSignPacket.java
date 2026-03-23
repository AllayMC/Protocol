package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to open a sign for editing. As of 1.19.80, the player can interact with a sign
 * to edit the text on both sides instead of just the front.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class OpenSignPacket implements BedrockPacket {
  private Vector3i position;
  private boolean frontSide;

  @Override
  public PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.OPEN_SIGN;
  }

  @Override
  public OpenSignPacket clone() {
    try {
      return (OpenSignPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
