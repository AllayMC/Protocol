package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.MovementEffectType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client to update specific movement effects to allow the client to
 * predict its movement. For example, fireworks used during gliding will send this packet to tell
 * the client the exact duration of the boost.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MovementEffectPacket implements BedrockPacket {
  private long entityRuntimeId;
  private MovementEffectType effectType;
  private int duration;
  private long tick;

  @Override
  public PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  @Override
  public BedrockPacketType getPacketType() {
    return BedrockPacketType.MOVEMENT_EFFECT;
  }

  @Override
  public MovementEffectPacket clone() {
    try {
      return (MovementEffectPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
