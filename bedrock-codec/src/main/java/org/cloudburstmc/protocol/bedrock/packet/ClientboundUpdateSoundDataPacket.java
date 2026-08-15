package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.sound.*;

/**
 * Sent by the server to update the state of a server-controlled sound.
 * Since v2168, each update is represented by an optional Cereal union slot.
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
     * The legacy textual sound action, which is no longer serialized in v2168 and newer.
     *
     * @deprecated since v2168
     */
    private String type;
    /**
     * The optional Cereal slot that fades the sound to a new volume.
     *
     * @since v2168
     */
    @Nullable
    private FadeSoundData fade;
    /**
     * The optional Cereal slot that pauses the sound.
     *
     * @since v2168
     */
    @Nullable
    private PauseSoundData pause;
    /**
     * The optional Cereal slot that resumes the sound.
     *
     * @since v2168
     */
    @Nullable
    private ResumeSoundData resume;
    /**
     * The optional Cereal slot that changes the sound playback position.
     *
     * @since v2168
     */
    @Nullable
    private SeekToSoundData seekTo;
    /**
     * The optional Cereal slot that changes the sound pitch.
     *
     * @since v2168
     */
    @Nullable
    private SetPitchSoundData pitch;
    /**
     * The optional Cereal slot that changes the sound volume.
     *
     * @since v2168
     */
    @Nullable
    private SetVolumeSoundData volume;
    /**
     * The optional Cereal slot that stops the sound.
     *
     * @since v2168
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
