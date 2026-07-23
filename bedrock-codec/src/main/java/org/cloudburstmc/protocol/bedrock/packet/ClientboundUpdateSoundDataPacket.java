package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.sound.*;

/**
 * Sent by the server to update the state of a server-controlled sound.
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
     * The sound event action to apply to the sound.
     *
     * @deprecated since v2168
     */
    private String type;
    /**
     * @since v2168
     */
    @Nullable
    private FadeSoundData fade;
    /**
     * @since v2168
     */
    @Nullable
    private PauseSoundData pause;
    /**
     * @since v2168
     */
    @Nullable
    private ResumeSoundData resume;
    /**
     * @since v2168
     */
    @Nullable
    private SeekToSoundData seekTo;
    /**
     * @since v2168
     */
    @Nullable
    private SetPitchSoundData pitch;
    /**
     * @since v2168
     */
    @Nullable
    private SetVolumeSoundData volume;
    /**
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
