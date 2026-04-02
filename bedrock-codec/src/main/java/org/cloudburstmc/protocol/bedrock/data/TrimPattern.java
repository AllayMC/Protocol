package org.cloudburstmc.protocol.bedrock.data;

/**
 * Describes an armor trim pattern entry advertised in {@link
 * org.cloudburstmc.protocol.bedrock.packet.TrimDataPacket}.
 *
 * @param itemName  the identifier of the smithing template item representing this pattern, for
 *                  example {@code minecraft:wayfinder_armor_trim_smithing_template}
 * @param patternId the protocol identifier of the pattern, for example {@code wayfinder}
 */
public record TrimPattern(String itemName, String patternId) {
}
