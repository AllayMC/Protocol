package org.cloudburstmc.protocol.bedrock.data.command;

import java.util.UUID;

/**
 * Identifies where a command request originated from. When sent in a {@code CommandRequestPacket},
 * the same origin should be echoed back in the corresponding {@code CommandOutputPacket}.
 *
 * @param origin    The command origin type, such as the player itself or a websocket server.
 * @param uuid      A unique identifier for every instantiation of a command.
 * @param requestId An ID that identifies the request of the client. The server should send a CommandOrigin with
 *                  the same request ID to ensure it can be matched with the request by the caller of the
 *                  command. This is especially important for websocket servers and it seems that this field is
 *                  only non-empty for these websocket servers.
 * @param playerId  The player's unique id, matching the one used in adventure settings. It is only written for
 *                  some origins such as developer console and test.
 */
public record CommandOriginData(CommandOriginType origin, UUID uuid, String requestId, long playerId) {
}
