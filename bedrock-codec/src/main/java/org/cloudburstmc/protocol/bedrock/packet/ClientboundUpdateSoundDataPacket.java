package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.sound.*;

/**
 * Updates a server-controlled sound. The v1001 format carries a fixed stop action.
 * Codecs from v2168 through v2193 carry seven operation slots, using STOP for an
 * unused slot. Since v2225 a packet carries one operation in {@link #event}.
 *
 * @since v1001
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundUpdateSoundDataPacket implements BedrockPacket {

    /**
     * The server-side handle identifying the sound to update.
     */
    private long serverSoundHandle;
    /**
     * The legacy textual sound action. Current v1001 encoding always writes "stop"
     * and ignores this property; decoding represents that action as {@link StopSoundData} in {@link #event}.
     *
     * @deprecated since v2168; operation slots replace the textual wire action.
     */
    private String type;
    /**
     * The single sound operation encoded from v2225. The v1001 decoder also sets this
     * to {@link StopSoundData}; codecs from v2168 through v2193 use the separate operation fields.
     *
     * @since v2225
     */
    private SoundData event;
    /**
     * The operation slot that fades the sound to a new volume. A null value writes a STOP placeholder.
     *
     * @since v2168
     * @deprecated since v2225; use {@link #event}. Older codecs still use the separate slots.
     */
    @Nullable
    private FadeSoundData fade;
    /**
     * The operation slot that pauses playback. A null value writes a STOP placeholder.
     *
     * @since v2168
     * @deprecated since v2225; use {@link #event}. Older codecs still use the separate slots.
     */
    @Nullable
    private PauseSoundData pause;
    /**
     * The operation slot that resumes playback. A null value writes a STOP placeholder.
     *
     * @since v2168
     * @deprecated since v2225; use {@link #event}. Older codecs still use the separate slots.
     */
    @Nullable
    private ResumeSoundData resume;
    /**
     * The operation slot that changes the playback position. A null value writes a STOP placeholder.
     *
     * @since v2168
     * @deprecated since v2225; use {@link #event}. Older codecs still use the separate slots.
     */
    @Nullable
    private SeekToSoundData seekTo;
    /**
     * The operation slot that changes the pitch. A null value writes a STOP placeholder.
     *
     * @since v2168
     * @deprecated since v2225; use {@link #event}. Older codecs still use the separate slots.
     */
    @Nullable
    private SetPitchSoundData pitch;
    /**
     * The operation slot that changes the volume. A null value writes a STOP placeholder.
     *
     * @since v2168
     * @deprecated since v2225; use {@link #event}. Older codecs still use the separate slots.
     */
    @Nullable
    private SetVolumeSoundData volume;
    /**
     * The decoded first operation slot. Codecs from v2168 through v2193 always write STOP here.
     *
     * @since v2168
     * @deprecated since v2225; use {@link #event}. Older codecs still use the separate slots.
     */
    @Nullable
    private StopSoundData stop;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_UPDATE_SOUND_DATA;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (ClientboundUpdateSoundDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
