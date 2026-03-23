package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * A packet sent from the server to the client to update the adventure settings of the player. It,
 * along with the UpdateAbilities packet, are replacements of the AdventureSettings packet since
 * v1.19.10.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateAdventureSettingsPacket implements BedrockPacket {
  private boolean noPvM;
  private boolean noMvP;
  private boolean immutableWorld;
  private boolean showNameTags;
  private boolean autoJump;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.UPDATE_ADVENTURE_SETTINGS;
  }

  @Override
  public UpdateAdventureSettingsPacket clone() {
    try {
      return (UpdateAdventureSettingsPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
