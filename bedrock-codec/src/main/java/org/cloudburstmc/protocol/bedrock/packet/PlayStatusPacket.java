package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to update a player on the play status. This includes failed statuses due to a
 * mismatched version, but also success statuses.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlayStatusPacket implements BedrockPacket {
    /**
     * The play status to apply. This is one of the values in {@link Status}.
     */
    private Status status;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAY_STATUS;
    }

    public enum Status {

        /**
         * Confirms login success and advances the connection into the resource-pack phase.
         */
        LOGIN_SUCCESS,

        /**
         * Indicates that the client is too old for the server.
         */
        LOGIN_FAILED_CLIENT_OLD,

        /**
         * Indicates that the server is too old for the client.
         */
        LOGIN_FAILED_SERVER_OLD,

        /**
         * Tells the client it may finish spawning into the world.
         */
        PLAYER_SPAWN,

        /**
         * Indicates that the tenant information in the login chain was invalid.
         */
        LOGIN_FAILED_INVALID_TENANT,

        /**
         * Sent when an Education Edition client attempts to join a vanilla Bedrock server.
         */
        LOGIN_FAILED_EDITION_MISMATCH_EDU_TO_VANILLA,

        /**
         * Sent when a vanilla Bedrock client attempts to join an Education Edition server.
         */
        LOGIN_FAILED_EDITION_MISMATCH_VANILLA_TO_EDU,

        /**
         * Sent to a split-screen sub-client when the server is already full.
         */
        FAILED_SERVER_FULL_SUB_CLIENT,

        /**
         * Sent when an editor client attempts to join a vanilla world.
         */
        EDITOR_TO_VANILLA_MISMATCH,

        /**
         * Sent when a vanilla client attempts to join an editor world.
         */
        VANILLA_TO_EDITOR_MISMATCH
    }

    @Override
    public PlayStatusPacket clone() {
        try {
            return (PlayStatusPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
