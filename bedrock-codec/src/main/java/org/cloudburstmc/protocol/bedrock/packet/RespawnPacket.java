package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make a player respawn client-side. It is sent in response to a PlayerAction
 * packet with ActionType PlayerActionRespawn. As of 1.13, the server sends two of these packets
 * with different states, and the client sends one of these back in order to complete the respawn.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RespawnPacket implements BedrockPacket {
    private Vector3f position;

    /**
     * @since v388
     */
    private State state;

    /**
     * @since v388
     */
    private long runtimeEntityId; // Only used server bound and pretty pointless

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESPAWN;
    }

    public enum State {
        SERVER_SEARCHING,
        SERVER_READY,
        CLIENT_READY
    }

    @Override
    public RespawnPacket clone() {
        try {
            return (RespawnPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
