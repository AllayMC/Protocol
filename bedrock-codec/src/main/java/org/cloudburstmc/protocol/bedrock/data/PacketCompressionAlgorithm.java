package org.cloudburstmc.protocol.bedrock.data;

/**
 * Enumerates packet compression algorithm values used in the Bedrock protocol.
 */
public enum PacketCompressionAlgorithm implements CompressionAlgorithm {
    ZLIB,
    SNAPPY,
    NONE
}
