package org.cloudburstmc.protocol.bedrock.packet;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to request a chunk of data from a particular resource pack, that it has
 * obtained information about in a ResourcePackDataInfo packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ResourcePackChunkRequestPacket implements BedrockPacket {
  private UUID packId;
  private String packVersion;
  private int chunkIndex;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.RESOURCE_PACK_CHUNK_REQUEST;
  }

  @Override
  public ResourcePackChunkRequestPacket clone() {
    try {
      return (ResourcePackChunkRequestPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
