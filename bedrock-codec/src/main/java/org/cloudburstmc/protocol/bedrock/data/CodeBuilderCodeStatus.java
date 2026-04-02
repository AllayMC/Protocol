package org.cloudburstmc.protocol.bedrock.data;

/**
 * CodeBuilder is an Education Edition packet sent by the server to the client to open the URL to a
 * Code Builder (websocket) server.
 */
public enum CodeBuilderCodeStatus {
    NONE,
    NOT_STARTED,
    IN_PROGRESS,
    PAUSED,
    ERROR,
    SUCCEEDED
}
