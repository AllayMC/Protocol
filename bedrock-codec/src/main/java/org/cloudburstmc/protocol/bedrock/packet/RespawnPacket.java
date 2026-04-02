package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;

/**
 * Sent by the server to make a player respawn client-side. It is sent in response to a PlayerAction
 * packet with ActionType PlayerActionRespawn. As of 1.13, the server sends two of these packets
 * with different states, and the client sends one of these back in order to complete the respawn.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RespawnPacket implements BedrockPacket {
    /**
     * The position on which the player should be respawned. The position might be in a different
     * dimension, in which case the client should first be sent a ChangeDimension packet.
     */
    private Vector3f position;
    /**
     * The 'state' of the respawn. It is one of the constants that may be found above, and the value
     * the packet contains depends on whether the server or client sends it.
     *
     * @since v388
     */
    private State state;
    /**
     * The runtime entity ID of the player that the respawn sequence concerns. This is mainly used
     * when the client sends the final respawn acknowledgement back to the server.
     *
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
        /**
         * The server is still searching for a valid spawn location.
         */
        SERVER_SEARCHING,
        /**
         * The server is ready for the client to respawn.
         */
        SERVER_READY,
        /**
         * Sent by the client to acknowledge that it is ready to complete the respawn.
         */
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
