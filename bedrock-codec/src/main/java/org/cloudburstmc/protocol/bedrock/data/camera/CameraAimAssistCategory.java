package org.cloudburstmc.protocol.bedrock.data.camera;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an aim assist category that defines priorities for specific blocks and entities.
 */
@Data
public class CameraAimAssistCategory {
    /**
     * The name of the category which can be used by a CameraAimAssistPreset.
     */
    private String name;
    /**
     * The entity priorities.
     */
    private List<CameraAimAssistPriority> entityPriorities = new ObjectArrayList<>();
    /**
     * The block priorities.
     */
    private List<CameraAimAssistPriority> blockPriorities = new ArrayList<>();
    /**
     * The entity default priorities.
     */
    @Nullable
    private Integer entityDefaultPriorities;
    /**
     * The block default priorities.
     */
    @Nullable
    private Integer blockDefaultPriorities;
    /**
     * The block tag priorities.
     *
     * @since v898
     */
    private List<CameraAimAssistPriority> blockTagPriorities = new ArrayList<>();
    /**
     * The entity type families priorities.
     *
     * @since v924
     */
    private List<CameraAimAssistPriority> entityTypeFamiliesPriorities = new ArrayList<>();
}
