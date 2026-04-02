package org.cloudburstmc.protocol.bedrock.data.structure;

/**
 * Enumerates structure mirror values used in the Bedrock protocol.
 */
public enum StructureMirror {
    NONE,
    X,
    Z,
    XZ;

    private static final StructureMirror[] VALUES = StructureMirror.values();

    public static StructureMirror from(int id) {
        return VALUES[id];
    }
}
