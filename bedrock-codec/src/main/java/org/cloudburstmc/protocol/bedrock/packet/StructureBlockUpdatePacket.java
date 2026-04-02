package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureEditorData;

/**
 * Sent by the client when it updates a structure block through the in-game UI.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class StructureBlockUpdatePacket implements BedrockPacket {
    /**
     * The position of the structure block being updated.
     */
    private Vector3i blockPosition;
    /**
     * The structure block editor settings supplied by the client.
     */
    private StructureEditorData editorData;
    /**
     * Whether the block is currently powered by redstone.
     */
    private boolean powered;
    /**
     * Specifies if non-air blocks replace water or combine with water.
     *
     * @since v554
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
