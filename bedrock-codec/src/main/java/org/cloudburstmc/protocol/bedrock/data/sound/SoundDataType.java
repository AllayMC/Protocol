package org.cloudburstmc.protocol.bedrock.data.sound;

/**
 * Wire tags for sound operations. Codecs from v2168 through v2193 use STOP as the
 * placeholder in an unused operation slot; v2225 carries one selected operation.
 *
 * @since v2168
 */
public enum SoundDataType {
    STOP,
    SET_VOLUME,
    SET_PITCH,
    FADE,
    SEEK_TO,
    PAUSE,
    RESUME;

    private static final SoundDataType[] VALUES = SoundDataType.values();

    public static SoundDataType from(int id) {
        return VALUES[id];
    }
}
