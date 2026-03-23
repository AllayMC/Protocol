package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make an explosion happen. The explosion will break all blocks in the packet
 * client-side.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ExplodePacket implements BedrockPacket {
  private final List<Vector3i> records = new ObjectArrayList<>();
  private Vector3f position;
  private float radius;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.EXPLODE;
  }

  @Override
  public ExplodePacket clone() {
    try {
      return (ExplodePacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
