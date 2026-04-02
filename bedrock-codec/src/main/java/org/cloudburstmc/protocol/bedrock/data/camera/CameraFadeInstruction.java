package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * CameraInstructionFade represents a camera instruction that fades the screen to a specified
 * colour.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraFadeInstruction {
    /**
     * The time data for the fade, which includes the fade in duration, wait duration and fade out
     * duration.
     */
    private TimeData timeData;
    /**
     * The colour of the screen to fade to. This only uses the red, green and blue components.
     */
    private Color color;

    public record TimeData(float fadeInTime, float waitTime, float fadeOutTime) {
    }
}
