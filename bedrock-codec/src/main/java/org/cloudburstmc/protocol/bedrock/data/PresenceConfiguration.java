package org.cloudburstmc.protocol.bedrock.data;

import org.checkerframework.checker.nullness.qual.Nullable;

public record PresenceConfiguration(@Nullable String experienceName, @Nullable String worldName,
                                    String richPresenceId) {
}
