package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to request the dealing damage to an anvil. This packet is completely pointless
 * and the server should never listen to it.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AnvilDamagePacket implements BedrockPacket {
    /**
     * The damage that the client requests to be dealt to the anvil.
     */
    private int damage;
    /**
     * The block position of the anvil that should be damaged.
     */
    private Vector3i position;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ANVIL_DAMAGE;
    }

    @Override
    public AnvilDamagePacket clone() {
        try {
            return (AnvilDamagePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
