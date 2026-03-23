package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to open a container client-side. This container must be physically present in
 * the world, for the packet to have any effect. Unlike Java Edition, Bedrock Edition requires that
 * chests for example must be present and in range to open its inventory.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ContainerOpenPacket implements BedrockPacket {
  private byte id;
  private ContainerType type;
  private Vector3i blockPosition;
  private long uniqueEntityId = -1;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.CONTAINER_OPEN;
  }

  @Override
  public ContainerOpenPacket clone() {
    try {
      return (ContainerOpenPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
