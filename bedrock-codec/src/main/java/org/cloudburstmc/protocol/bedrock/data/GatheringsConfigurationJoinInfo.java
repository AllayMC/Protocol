package org.cloudburstmc.protocol.bedrock.data;

import java.util.UUID;

public record GatheringsConfigurationJoinInfo(UUID experienceId, String experienceName, UUID worldId, String worldName,
                                              String creatorId, UUID targetId, String scenarioId, String serverId) {
}
