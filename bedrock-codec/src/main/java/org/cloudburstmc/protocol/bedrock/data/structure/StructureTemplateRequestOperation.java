package org.cloudburstmc.protocol.bedrock.data.structure;

/**
 * Enumerates the operations a client may request in
 * {@code StructureTemplateDataRequestPacket}.
 */
public enum StructureTemplateRequestOperation {
    NONE,
    EXPORT_FROM_SAVED_MODE,
    EXPORT_FROM_LOAD_MODE,
    QUERY_SAVED_STRUCTURE,
    /**
     * @since v560
     * @deprecated since v712
     */
    @Deprecated
    IMPORT;

    private static final StructureTemplateRequestOperation[] VALUES = StructureTemplateRequestOperation.values();

    public static StructureTemplateRequestOperation from(int id) {
        return VALUES[id];
    }
}
