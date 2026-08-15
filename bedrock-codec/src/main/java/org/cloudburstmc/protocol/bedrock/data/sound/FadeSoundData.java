package org.cloudburstmc.protocol.bedrock.data.sound;

/**
 * Requests a fade of a server-controlled sound.
 *
 * @param targetVolume the volume at the end of the fade
 * @param duration     the fade duration in seconds
 * @since v2168
 */
public record FadeSoundData(float targetVolume, float duration) {
}
