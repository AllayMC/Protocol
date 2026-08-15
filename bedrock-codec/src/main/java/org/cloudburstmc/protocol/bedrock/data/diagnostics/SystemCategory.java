package org.cloudburstmc.protocol.bedrock.data.diagnostics;

/**
 * Maps a client diagnostics category name to the system index used by timing diagnostics.
 *
 * @param categoryName the diagnostics category name
 * @param systemIndex  the index referenced by system timing entries
 * @since v2168
 */
public record SystemCategory(String categoryName, long systemIndex) {
}
