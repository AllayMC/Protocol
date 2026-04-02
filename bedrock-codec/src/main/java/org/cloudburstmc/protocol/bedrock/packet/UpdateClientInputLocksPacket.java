package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

import static org.cloudburstmc.protocol.bedrock.packet.BedrockPacketType.UPDATE_CLIENT_INPUT_LOCKS;

/**
 * Sent by the server to the client to lock specific player inputs such as camera rotation,
 * movement, jumping, sneaking, mounting, or directional movement.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateClientInputLocksPacket implements BedrockPacket {
    /**
     * A bitset describing which client inputs should be disabled.
     */
    private int lockComponentData;
    /**
     * Legacy server position field used by older protocol versions.
     *
     * @deprecated since v944
     */
    @Deprecated
    private Vector3f serverPosition;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return UPDATE_CLIENT_INPUT_LOCKS;
    }

    @Override
    public UpdateClientInputLocksPacket clone() {
        try {
            return (UpdateClientInputLocksPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
