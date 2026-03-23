package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to play a sound to the client. Some of the sounds may only be started using
 * this packet and must be stopped using the StopSound packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlaySoundPacket implements BedrockPacket {
  private String sound;
  private Vector3f position;
  private float volume;
  private float pitch;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.PLAY_SOUND;
  }

  @Override
  public PlaySoundPacket clone() {
    try {
      return (PlaySoundPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
