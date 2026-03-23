package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to the server and the server to the client to make the other side aware of the
 * new item that an entity is holding. It is used to show the item in the hand of entities such as
 * zombies too.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MobEquipmentPacket implements BedrockPacket {
  private long runtimeEntityId;
  private ItemData item;
  private int inventorySlot;
  private int hotbarSlot;
  private int containerId;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.MOB_EQUIPMENT;
  }

  @Override
  public MobEquipmentPacket clone() {
    try {
      return (MobEquipmentPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
