package org.cloudburstmc.protocol.bedrock.data;

/**
 * Enumerates chat restriction level values used in the Bedrock protocol.
 */
public enum ChatRestrictionLevel {
    /**
     * Default behaviour.
     */
    NONE,
    /**
     * The chat window will appear, but all messages will be dropped.
     */
    DROPPED,
    /**
     * The chat window will not show at all, unless you have the
     * {@link PlayerPermission#OPERATOR} permission set in your abilities.
     */
    DISABLED
}
