package org.cloudburstmc.protocol.bedrock.data.sound;

/**
 * Requests a volume change for a server-controlled sound.
 *
 * @param volume the new playback volume
 * @since v2168
 */
public record SetVolumeSoundData(float volume) {
}
