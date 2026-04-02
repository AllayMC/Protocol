package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.AttributeData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Sent by the server to update one or more synced attributes for an entity, such as health or
 * movement speed.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateAttributesPacket implements BedrockPacket {
    /**
     * The runtime ID of the entity whose attributes are being updated. Runtime IDs are scoped to
     * the current session and are the identifier used for entities in most packets.
     */
    private long runtimeEntityId;
    /**
     * The changed attribute values to apply. Servers typically only send entries whose values have
     * changed rather than the entity's full attribute set.
     */
    private List<AttributeData> attributes = new ObjectArrayList<>();
    /**
     * The server tick at which the update was produced. This is used alongside
     * {@link CorrectPlayerMovePredictionPacket}.
     *
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
