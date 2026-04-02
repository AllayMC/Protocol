package org.cloudburstmc.protocol.bedrock.data.command;

/**
 * CommandOutputMessage represents a message sent by a command that holds the output of one of the
 * commands executed.
 */
public enum CommandOutputType {
    NONE,
    LAST_OUTPUT,
    SILENT,
    ALL_OUTPUT,
    DATA_SET
}
