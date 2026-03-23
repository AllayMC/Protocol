package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the both the client and the server. The client sends the packet to the server to allow
 * the server to filter the text server-side. The server then responds with the same packet and the
 * safer version of the text. Deprecated: This packet was deprecated in unknown.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class FilterTextPacket implements BedrockPacket {
  private String text;
  private boolean fromServer;

  @Override
  public PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.FILTER_TEXT;
  }

  @Override
  public FilterTextPacket clone() {
    try {
      return (FilterTextPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
