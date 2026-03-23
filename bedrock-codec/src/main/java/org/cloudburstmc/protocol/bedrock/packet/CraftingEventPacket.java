package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.CraftingType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client when it crafts a particular item. This packet may be fully ignored, as the
 * InventoryTransaction packet provides all the information required.
 *
 * @since v630
 */
@Deprecated
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CraftingEventPacket implements BedrockPacket {
  private final List<ItemData> inputs = new ObjectArrayList<>();
  private final List<ItemData> outputs = new ObjectArrayList<>();
  private byte containerId;
  private CraftingType type;
  private UUID uuid;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.CRAFTING_EVENT;
  }

  @Override
  public CraftingEventPacket clone() {
    try {
      return (CraftingEventPacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
