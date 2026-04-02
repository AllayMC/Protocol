package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;

/**
 * Sent by the server to update the available block properties.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateBlockPropertiesPacket implements BedrockPacket {
    /**
     * The network NBT compound containing the block property registry advertised to the client.
     */
    private NbtMap properties;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_BLOCK_PROPERTIES;
    }

    @Override
    public UpdateBlockPropertiesPacket clone() {
        try {
            return (UpdateBlockPropertiesPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
