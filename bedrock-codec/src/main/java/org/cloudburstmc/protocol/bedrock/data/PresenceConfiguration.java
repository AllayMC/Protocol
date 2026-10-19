package org.cloudburstmc.protocol.bedrock.data;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Optional presence metadata advertised by the server.
 *
 * @param experienceName the optional experience name; deprecated since v2168 and ignored by newer codecs
 * @param worldName      the optional world name; deprecated since v2168 and ignored by newer codecs
 * @param richPresenceId the rich presence identifier; required in v1001, optional since v2168
 * @since v1001
 */
public record PresenceConfiguration(@Nullable String experienceName, @Nullable String worldName,
                                    String richPresenceId) {
}
