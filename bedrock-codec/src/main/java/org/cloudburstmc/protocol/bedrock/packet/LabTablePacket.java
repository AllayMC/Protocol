package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.inventory.LabTableReactionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.LabTableType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to let the server know it started a chemical reaction in Education Edition,
 * and is sent by the server to other clients to show the effects. The packet is only functional if
 * Education features are enabled.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LabTablePacket implements BedrockPacket {
  private LabTableType type;
  private Vector3i position;
  private LabTableReactionType reactionType;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.LAB_TABLE;
  }

  @Override
  public LabTablePacket clone() {
    try {
      return (LabTablePacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
