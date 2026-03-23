package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make any kind of built-in sound heard to a player. It is sent to, for
 * example, play a stepping sound or a shear sound. The packet is also sent by the client, in which
 * case it could be forwarded by the server to the other players online. If possible, the packets
 * from the client should be ignored however, and the server should play them on its own accord.
 *
 * @deprecated since v786
 */
@Deprecated
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LevelSoundEvent2Packet implements BedrockPacket {
  private SoundEvent sound;
  private Vector3f position;
  private int extraData;
  private String identifier;
  private boolean babySound;
  private boolean relativeVolumeDisabled;

  @Override
  public PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.LEVEL_SOUND_EVENT_2;
  }

  @Override
  public LevelSoundEvent2Packet clone() {
    try {
      return (LevelSoundEvent2Packet) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
