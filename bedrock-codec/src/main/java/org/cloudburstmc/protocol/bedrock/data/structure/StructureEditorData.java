package org.cloudburstmc.protocol.bedrock.data.structure;

import lombok.Value;

@Value
public class StructureEditorData {
    private final String name;
    /**
     * @since v361
     */
    private final String dataField;
    /**
     * @since v388
     */
    private final StructureRedstoneSaveMode redstoneSaveMode;
    /**
     * @since v776
     */
    private final String filteredName;
    private final boolean includingPlayers;
    private final boolean boundingBoxVisible;
    private final StructureBlockType type;
    private final StructureSettings settings;

    public StructureEditorData(String name, String filteredName, String dataField, boolean includingPlayers,
                               boolean boundingBoxVisible, StructureBlockType type, StructureSettings settings,
                               StructureRedstoneSaveMode redstoneSaveMode) {
        this.name = name;
        this.dataField = dataField;
        this.redstoneSaveMode = redstoneSaveMode;
        this.filteredName = filteredName;
        this.includingPlayers = includingPlayers;
        this.boundingBoxVisible = boundingBoxVisible;
        this.type = type;
        this.settings = settings;
    }
}
