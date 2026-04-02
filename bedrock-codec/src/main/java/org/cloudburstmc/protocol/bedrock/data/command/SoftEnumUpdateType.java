package org.cloudburstmc.protocol.bedrock.data.command;

/**
 * UpdateSoftEnum is sent by the server to update a soft enum, also known as a dynamic enum,
 * previously sent in the AvailableCommands packet. It is sent whenever the enum should get new
 * options or when some of its options should be removed. The UpdateSoftEnum packet will apply for
 * enums that have been set in the AvailableCommands packet with the 'Dynamic' field of the
 * CommandEnum set to true.
 */
public enum SoftEnumUpdateType {
    ADD,
    REMOVE,
    REPLACE
}
