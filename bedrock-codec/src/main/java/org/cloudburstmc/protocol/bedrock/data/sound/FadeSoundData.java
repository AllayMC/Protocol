package org.cloudburstmc.protocol.bedrock.data.sound;

import lombok.Value;

@Value
public class FadeSoundData {
    float targetVolume;
    float duration;
}
