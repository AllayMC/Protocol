package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.definitions.DimensionDefinition;

import java.util.List;

/**
 * Sent by the server to register data-driven dimensions before {@link StartGamePacket} in the
 * login sequence. This packet is typically only sent when custom dimension definitions are
 * present.
 *
 * <p><b>Note:</b> As of Bedrock 1.18.30 the client only accepts the
 * {@code minecraft:overworld} definition.
 *
 * @since v503
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class DimensionDataPacket implements BedrockPacket {
    /**
     * Definitions contain a list of data-driven dimension definitions registered on the server.
     */
    private final List<DimensionDefinition> definitions = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.DIMENSION_DATA;
    }

    @Override
    public DimensionDataPacket clone() {
        try {
            return (DimensionDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
