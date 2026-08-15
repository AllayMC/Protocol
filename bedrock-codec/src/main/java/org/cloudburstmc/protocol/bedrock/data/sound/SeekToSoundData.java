package org.cloudburstmc.protocol.bedrock.data.sound;

/**
 * Requests that a server-controlled sound seek to a playback position.
 *
 * @param seconds the new playback position in seconds
 * @since v2168
 */
public record SeekToSoundData(float seconds) {
}
