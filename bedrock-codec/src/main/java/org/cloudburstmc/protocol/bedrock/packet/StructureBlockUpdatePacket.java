package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureEditorData;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client when it updates a structure block using the in-game UI. The data it contains
 * depends on the type of structure block that it is. In Minecraft Bedrock Edition v1.11, there is
 * only the Export structure block type, but in v1.13 the ones present in Java Edition will,
 * according to the wiki, be added too.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class StructureBlockUpdatePacket implements BedrockPacket {
  private Vector3i blockPosition;
  private StructureEditorData editorData;
  private boolean powered;

  /**
   * @since v553
   */
  private boolean waterlogged;

  @Override
  public final PacketSignal handle(BedrockPacketHandler handler) {
    return handler.handle(this);
  }

  public BedrockPacketType getPacketType() {
    return BedrockPacketType.STRUCTURE_BLOCK_UPDATE;
  }

  public enum Type {
    NONE,
    SAVE,
    LOAD,
  }

  @Override
  public StructureBlockUpdatePacket clone() {
    try {
      return (StructureBlockUpdatePacket) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    }
  }
}
