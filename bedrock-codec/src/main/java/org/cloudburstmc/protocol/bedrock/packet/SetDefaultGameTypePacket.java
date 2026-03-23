package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client when it toggles the default game type in the settings UI, and is sent by the
 * server when it actually changes the default game type, resulting in the toggle being changed in
 * the settings UI.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetDefaultGameTypePacket implements BedrockPacket {
  private int gamemode;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.SET_DEFAULT_GAME_TYPE;
  }

  @Override
  public SetDefaultGameTypePacket clone() {
    try {
      return (SetDefaultGameTypePacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
