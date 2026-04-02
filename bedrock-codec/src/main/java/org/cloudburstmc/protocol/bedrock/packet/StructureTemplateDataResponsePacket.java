package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureTemplateResponseType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server in response to {@link StructureTemplateDataRequestPacket}.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class StructureTemplateDataResponsePacket implements BedrockPacket {
    /**
     * The structure template name.
     */
    private String name;
    /**
     * Whether the response contains data for a save/export operation.
     */
    private boolean save;
    /**
     * The structure template data encoded as NBT.
     */
    private NbtMap tag;
    /**
     * The response operation type.
     *
     * @since v388
     */
    private StructureTemplateResponseType type;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.STRUCTURE_TEMPLATE_DATA_EXPORT_RESPONSE;
    }

    @Override
    public StructureTemplateDataResponsePacket clone() {
        try {
            return (StructureTemplateDataResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
