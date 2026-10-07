package org.cloudburstmc.protocol.bedrock.data.sound;

/**
 * Requests that a paused server-controlled sound resume its playback.
 *
 * @since v2168
 */
public record ResumeSoundData() implements SoundData {

    @Override
    public SoundDataType getType() {
        return SoundDataType.RESUME;
    }
}
