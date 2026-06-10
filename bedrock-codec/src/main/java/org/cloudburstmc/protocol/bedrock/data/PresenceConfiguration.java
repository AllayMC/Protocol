package org.cloudburstmc.protocol.bedrock.data;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Optional presence metadata advertised by the server.
 *
 * @param experienceName The optional experience name.
 * @param worldName The optional world name.
 * @param richPresenceId The rich presence identifier.
 *
 * @since v1001
 */
public record PresenceConfiguration(@Nullable String experienceName, @Nullable String worldName,
                                    String richPresenceId) {
}
