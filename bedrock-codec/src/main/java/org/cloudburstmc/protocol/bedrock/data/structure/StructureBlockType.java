package org.cloudburstmc.protocol.bedrock.data.structure;

/**
 * StructureBlockUpdate is sent by the client when it updates a structure block using the in-game
 * UI. The data it contains depends on the type of structure block that it is. In Minecraft Bedrock
 * Edition v1.11, there is only the Export structure block type, but in v1.13 the ones present in
 * Java Edition will, according to the wiki, be added too.
 */
public enum StructureBlockType {
    DATA,
    SAVE,
    LOAD,
    CORNER,
    INVALID,
    EXPORT;

    private static final StructureBlockType[] VALUES = StructureBlockType.values();

    public static StructureBlockType from(int id) {
        return VALUES[id];
    }
}
