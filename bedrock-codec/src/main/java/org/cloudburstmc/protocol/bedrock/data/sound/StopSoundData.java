package org.cloudburstmc.protocol.bedrock.data.sound;

/**
 * Requests that a server-controlled sound stop its playback.
 *
 * @since v2168
 */
public record StopSoundData() implements SoundData {

    @Override
    public SoundDataType getType() {
        return SoundDataType.STOP;
    }
}
