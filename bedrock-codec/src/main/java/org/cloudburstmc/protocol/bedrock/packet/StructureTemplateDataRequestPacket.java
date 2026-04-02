package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureTemplateRequestOperation;

/**
 * Sent by the client to request structure template data from the server.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class StructureTemplateDataRequestPacket implements BedrockPacket {
    /**
     * The structure template name.
     */
    private String name;
    /**
     * The position of the structure block that has its template data requested.
     */
    private Vector3i position;
    /**
     * The structure settings to use when fulfilling the request.
     */
    private StructureSettings settings;
    /**
     * The requested template operation.
     */
    private StructureTemplateRequestOperation operation;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.STRUCTURE_TEMPLATE_DATA_EXPORT_REQUEST;
    }

    @Override
    public StructureTemplateDataRequestPacket clone() {
        try {
            return (StructureTemplateDataRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
