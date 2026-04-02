package org.cloudburstmc.protocol.bedrock.data.camera;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;

import java.util.List;

/**
 * A named collection of aim assist categories that presets may reference.
 */
@Data
public class CameraAimAssistCategories {
    /**
     * The identifier.
     */
    private String identifier;
    /**
     * The categories.
     */
    private final List<CameraAimAssistCategory> categories = new ObjectArrayList<>();
}
