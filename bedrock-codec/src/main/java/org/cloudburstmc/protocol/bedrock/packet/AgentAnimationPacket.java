package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * An Education Edition packet sent from the server to the client to make an agent perform an
 * animation.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AgentAnimationPacket implements BedrockPacket {
  private byte animation;
  private long runtimeEntityId;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.AGENT_ANIMATION;
  }

  @Override
  public AgentAnimationPacket clone() {
    try {
      return (AgentAnimationPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
