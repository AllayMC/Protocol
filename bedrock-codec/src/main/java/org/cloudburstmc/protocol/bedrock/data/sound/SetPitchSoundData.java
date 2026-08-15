package org.cloudburstmc.protocol.bedrock.data.sound;

/**
 * Requests a pitch change for a server-controlled sound.
 *
 * @param pitch the new playback pitch
 * @since v2168
 */
public record SetPitchSoundData(float pitch) {
}
