package org.cloudburstmc.protocol.bedrock.data;

/**
 * PacketViolationWarning is sent by the client when it receives an invalid packet from the server.
 * It holds some information on the error that occurred.
 */
public enum PacketViolationType {
    UNKNOWN,
    MALFORMED_PACKET
}
