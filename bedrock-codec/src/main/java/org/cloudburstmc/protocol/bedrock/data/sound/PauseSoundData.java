package org.cloudburstmc.protocol.bedrock.data.sound;

/**
 * Requests that a server-controlled sound pause its playback.
 *
 * @since v2168
 */
public record PauseSoundData() implements SoundData {

    @Override
    public SoundDataType getType() {
        return SoundDataType.PAUSE;
    }
}
