package org.cloudburstmc.protocol.bedrock.data;

import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;

/**
 * The movement authority mode selected by the server for validating player movement.
 */
public enum AuthoritativeMovementMode {
    /**
     * Movement is completely controlled by the client and does not use
     * {@link PlayerAuthInputPacket}.
     *
     * @deprecated v800
     */
    @Deprecated
    CLIENT,
    /**
     * Movement is validated by the server using {@link PlayerAuthInputPacket}.
     */
    SERVER,
    /**
     * Movement is server authoritative with rewind-based correction.
     */
    SERVER_WITH_REWIND
}
