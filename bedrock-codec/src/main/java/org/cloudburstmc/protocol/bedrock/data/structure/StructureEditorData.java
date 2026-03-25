package org.cloudburstmc.protocol.bedrock.data.structure;

import lombok.Value;

@Value
public class StructureEditorData {
    private final String name;
    /**
     * @since v776
     */
    private final String filteredName;
    /**
     * @since v361
     */
    private final String dataField;
    private final boolean includingPlayers;
    private final boolean boundingBoxVisible;
    private final StructureBlockType type;
    private final StructureSettings settings;
    /**
     * @since v388
     */
    private final StructureRedstoneSaveMode redstoneSaveMode;
}
