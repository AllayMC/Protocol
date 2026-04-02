package org.cloudburstmc.protocol.bedrock.data.structure;

/**
 * Enumerates the structure template response modes used in
 * {@code StructureTemplateDataResponsePacket}.
 */
public enum StructureTemplateResponseType {
    NONE,
    EXPORT,
    QUERY,
    /**
     * @since v560
     * @deprecated since v712
     */
    @Deprecated
    IMPORT;

    private static final StructureTemplateResponseType[] VALUES = StructureTemplateResponseType.values();

    public static StructureTemplateResponseType from(int id) {
        return VALUES[id];
    }
}
