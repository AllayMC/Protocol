package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server in response to a RequestChunkRadius packet. It defines the chunk radius that
 * the server allows the client to have. This may be lower than the chunk radius requested by the
 * client in the RequestChunkRadius packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ChunkRadiusUpdatedPacket implements BedrockPacket {
  private int radius;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.CHUNK_RADIUS_UPDATED;
  }

  @Override
  public ChunkRadiusUpdatedPacket clone() {
    try {
      return (ChunkRadiusUpdatedPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
