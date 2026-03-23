package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to remove an entity that currently exists in the world from the client- side.
 * Sending this packet if the client cannot already see this entity will have no effect.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RemoveEntityPacket implements BedrockPacket {
  private long uniqueEntityId;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.REMOVE_ENTITY;
  }

  @Override
  public RemoveEntityPacket clone() {
    try {
      return (RemoveEntityPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
