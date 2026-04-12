package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.primitiveshape.PrimitiveShape;

import java.util.List;

/**
 * Sent by the server to instruct the client to render one or more primitive shapes. Each packet
 * replaces the previously rendered shape state.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PrimitiveShapesPacket implements BedrockPacket {

    /**
     * The shapes to render client-side. Omitting a previously sent shape removes it on the next
     * update.
     */
    private final List<PrimitiveShape> shapes = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PRIMITIVE_SHAPES;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (PrimitiveShapesPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
