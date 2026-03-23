package org.cloudburstmc.protocol.bedrock.packet;

import static org.cloudburstmc.protocol.bedrock.packet.BedrockPacketType.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client to lock either the camera or physical movement of the client.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateClientInputLocksPacket implements BedrockPacket {
    private int lockComponentData;

    /**
     * @deprecated since v944
     */
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
