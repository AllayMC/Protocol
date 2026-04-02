package org.cloudburstmc.protocol.bedrock.data;

/**
 * Enumerates the kinds of resource packs that may be advertised or transferred in the Bedrock
 * protocol.
 */
public enum ResourcePackType {
    INVALID,
    RESOURCES,
    DATA_ADD_ON,
    WORLD_TEMPLATE,
    ADDON,
    SKINS,
    CACHED,
    COPY_PROTECTED,
    PERSONA_PIECE
}
