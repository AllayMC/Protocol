package org.cloudburstmc.protocol.bedrock.data.camera;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * CameraAimAssistPreset defines a base preset that can be extended upon when sending an aim assist.
 */
@Data
public class CameraAimAssistPresetDefinition {
    /**
     * Identifier represents the identifier of this preset.
     */
    private String identifier;
    /**
     * The categories.
     *
     * @deprecated since v776
     */
    @Deprecated
    private String categories;
    /**
     * The exclusion list.
     *
     * @deprecated since v897
     */
    @Deprecated
    private final List<String> exclusionList = new ObjectArrayList<>();
    /**
     * The liquid targeting list.
     */
    private final List<String> liquidTargetingList = new ObjectArrayList<>();
    /**
     * A list of settings for specific item identifiers. If an item is not listed here, it will
     * fallback to DefaultItemSettings or HandSettings if no item is held.
     */
    private final List<CameraAimAssistItemSettings> itemSettings = new ObjectArrayList<>();
    /**
     * The identifier of a category to use when the player is not holding an item listed in
     * ItemSettings. This must be the identifier of a category within the Categories slice.
     */
    @Nullable
    private String defaultItemSettings;
    /**
     * The identifier of a category to use when the player is not holding an item. This must be the
     * identifier of a category within Categories slice.
     */
    @Nullable
    private String handSettings;
    /**
     * The block exclusion list.
     *
     * @since v898
     */
    private final List<String> blockExclusionList = new ObjectArrayList<>();
    /**
     * The block tag exclusion list.
     *
     * @since v898
     */
    private final List<String> blockTagExclusionList = new ObjectArrayList<>();
    /**
     * The entity exclusion list.
     *
     * @since v898
     */
    private final List<String> entityExclusionList = new ObjectArrayList<>();
    /**
     * The entity type families exclusion list.
     *
     * @since v924
     */
    private final List<String> entityTypeFamiliesExclusionList = new ObjectArrayList<>();
}
