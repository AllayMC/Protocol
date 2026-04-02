package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;

/**
 * Sent by the server at the start of the game to let the client know all entities that are
 * available on the server.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AvailableEntityIdentifiersPacket implements BedrockPacket {
    /**
     * The NBT payload describing every entity identifier that the client may encounter in this
     * session.
     */
    private NbtMap identifiers;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.AVAILABLE_ENTITY_IDENTIFIERS;
    }

    @Override
    public AvailableEntityIdentifiersPacket clone() {
        try {
            return (AvailableEntityIdentifiersPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
