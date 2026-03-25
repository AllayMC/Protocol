package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.AttributeData;
import org.cloudburstmc.protocol.common.PacketSignal;
import java.util.List;

/**
 * Sent by the server to update an amount of attributes of any entity in the world. These attributes
 * include ones such as the health or the movement speed of the entity.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateAttributesPacket implements BedrockPacket {
    private long runtimeEntityId;
    private List<AttributeData> attributes = new ObjectArrayList<>();
    /**
     * @since v419
     */
    private long tick;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_ATTRIBUTES;
    }

    @Override
    public UpdateAttributesPacket clone() {
        try {
            return (UpdateAttributesPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
