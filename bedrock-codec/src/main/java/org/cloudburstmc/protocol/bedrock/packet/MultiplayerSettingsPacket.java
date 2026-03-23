package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.MultiplayerMode;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to update multi-player related settings server-side and sent back to online
 * players by the server. The MultiPlayerSettings packet is a Minecraft: Education Edition packet.
 * It has no functionality for the base game.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MultiplayerSettingsPacket implements BedrockPacket {
  private MultiplayerMode mode;

  @Override
  public PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.MULTIPLAYER_SETTINGS;
  }

  @Override
  public MultiplayerSettingsPacket clone() {
    try {
      return (MultiplayerSettingsPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
