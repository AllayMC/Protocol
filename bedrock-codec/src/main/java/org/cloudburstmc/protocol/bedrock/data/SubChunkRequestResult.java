package org.cloudburstmc.protocol.bedrock.data;

/**
 * SubChunkRequest requests specific sub-chunks from the server using a center point.
 */
public enum SubChunkRequestResult {
    UNDEFINED,
    SUCCESS,
    CHUNK_NOT_FOUND,
    INVALID_DIMENSION,
    PLAYER_NOT_FOUND,
    INDEX_OUT_OF_BOUNDS,
    SUCCESS_ALL_AIR
}
