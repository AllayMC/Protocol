package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cloudburstmc.math.vector.Vector3f;

import java.util.List;

@Data
@AllArgsConstructor
public class CameraSplineInstruction {

    private float totalTime;
    private CameraSplineType type;
    private List<Vector3f> curve;
    private List<SplineProgressOption> progressKeyFrames;
    private List<SplineRotationOption> rotationOption;
    // TODO: what's "splineIdentifier and loadFromJson flag" from 916?

    @Data
    @AllArgsConstructor
    public static class SplineProgressOption {

        private float value;
        private float time;
        private CameraEase easingFunc;
    }

    @Data
    @AllArgsConstructor
    public static class SplineRotationOption {

        private Vector3f keyFrameValues;
        private float keyFrameTimes;
    }
}
