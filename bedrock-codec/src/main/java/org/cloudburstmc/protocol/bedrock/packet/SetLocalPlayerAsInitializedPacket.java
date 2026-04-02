package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the client once the local player has fully spawned and the client is ready to process the
 * rest of the gameplay stream without discarding packets.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetLocalPlayerAsInitializedPacket implements BedrockPacket {
    /**
     * The runtime entity ID assigned to the local player earlier in {@link StartGamePacket}.
     */
    private long runtimeEntityId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_LOCAL_PLAYER_AS_INITIALIZED;
    }

    @Override
    public SetLocalPlayerAsInitializedPacket clone() {
        try {
            return (SetLocalPlayerAsInitializedPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
