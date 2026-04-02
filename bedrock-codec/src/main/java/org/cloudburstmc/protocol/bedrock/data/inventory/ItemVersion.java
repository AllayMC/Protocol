package org.cloudburstmc.protocol.bedrock.data.inventory;

/**
 * Enumerates item version values used in the Bedrock protocol.
 */
public enum ItemVersion {
    LEGACY,
    DATA_DRIVEN,
    NONE;

    private static final ItemVersion[] VALUES = values();

    public static ItemVersion from(int ordinal) {
        return VALUES[ordinal];
    }
}
