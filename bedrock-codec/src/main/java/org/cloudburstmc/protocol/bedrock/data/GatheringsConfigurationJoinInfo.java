package org.cloudburstmc.protocol.bedrock.data;

import java.util.UUID;

/**
 * Describes the optional Gatherings experience metadata carried with a server transfer.
 *
 * @param experienceId   the identifier of the experience to join
 * @param experienceName the display name of the experience
 * @param worldId        the optional identifier of the destination world
 * @param worldName      the optional display name of the destination world
 * @param creatorId      the identifier of the experience creator
 * @param targetId       the optional identifier of the transfer target
 * @param scenarioId     the optional scenario identifier
 * @param serverId       the optional Gatherings server identifier
 * @since v2168
 */
public record GatheringsConfigurationJoinInfo(UUID experienceId, String experienceName, UUID worldId, String worldName,
                                              String creatorId, UUID targetId, String scenarioId, String serverId) {
}
