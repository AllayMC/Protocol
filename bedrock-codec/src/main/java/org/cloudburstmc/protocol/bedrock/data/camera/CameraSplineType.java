package org.cloudburstmc.protocol.bedrock.data.camera;

import java.util.HashMap;
import java.util.Map;

/**
 * CameraSplineInstruction represents a camera instruction that creates a spline path for the camera
 * to follow.
 */
public enum CameraSplineType {

    CATMULL_ROM("catmullrom"),
    LINEAR("linear");

    /**
     * The serialize names.
     */
    private static final Map<String, CameraSplineType> serializeNames = new HashMap<>();

    static {
        for (CameraSplineType value : values()) {
            serializeNames.put(value.getSerializeName(), value);
        }
    }

    /**
     * The serialize name.
     *
     * @since v924
     */
    private final String serializeName;

    CameraSplineType(String serializeName) {
        this.serializeName = serializeName;
    }

    public String getSerializeName() {
        return this.serializeName;
    }

    public static CameraSplineType fromName(String serializeName) {
        return serializeNames.get(serializeName);
    }
}
