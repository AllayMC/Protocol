package org.cloudburstmc.protocol.bedrock.data.command;

/**
 * Represents sent in the AvailableCommands packet to limit what values of an enum may be used
 * taking in account things such as whether cheats are enabled.
 */
public enum CommandEnumConstraint {
    CHEATS_ENABLED,
    OPERATOR_PERMISSIONS,
    HOST_PERMISSIONS,
    ALLOW_ALIASES
}
