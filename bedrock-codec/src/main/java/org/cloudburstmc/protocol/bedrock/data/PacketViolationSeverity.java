package org.cloudburstmc.protocol.bedrock.data;

/**
 * PacketViolationWarning is sent by the client when it receives an invalid packet from the server.
 * It holds some information on the error that occurred. noinspection GoNameStartsWithPackageName.
 */
public enum PacketViolationSeverity {
    UNKNOWN,
    WARNING,
    FINAL_WARNING,
    TERMINATING_CONNECTION
}
