package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cloudburstmc.math.vector.Vector3f;

import java.util.List;

/**
 * Represents a camera instruction that creates a spline path for the camera to follow.
 */
@Data
@AllArgsConstructor
public class CameraSplineInstruction {

    /**
     * The total time for the spline animation.
     */
    private float totalTime;
    /**
     * The type.
     */
    private CameraSplineType type;
    /**
     * A list of points that define the spline curve.
     */
    private List<Vector3f> curve;
    /**
     * A list of progress key frames for the spline.
     */
    private List<SplineProgressOption> progressKeyFrames;
    /**
     * The rotation option.
     */
    private List<SplineRotationOption> rotationOption;
    /**
     * SplineIdentifier is an optional identifier for referencing the spline by name.
     *
     * @since v924
     */
    private String splineIdentifier;
    /**
     * LoadFromJson optionally determines whether the spline should be loaded from a JSON
     * definition.
     *
     * @since v924
     */
    private boolean loadFromJson;

    public CameraSplineInstruction(float totalTime, CameraSplineType type, List<Vector3f> curve, List<SplineProgressOption> progressKeyFrames, List<SplineRotationOption> rotationOption) {
        this.totalTime = totalTime;
        this.type = type;
        this.curve = curve;
        this.progressKeyFrames = progressKeyFrames;
        this.rotationOption = rotationOption;
        this.splineIdentifier = "";
        this.loadFromJson = false;
    }

    @Data
    @AllArgsConstructor
    public static class SplineProgressOption {

        private float value;
        private float time;
        /**
         * @since v924
         */
        private CameraEase ease;
    }

    @Data
    @AllArgsConstructor
    public static class SplineRotationOption {

        private Vector3f keyFrameValues;
        private float keyFrameTimes;
        /**
         * @since v924
         */
        private CameraEase ease;
    }
}
