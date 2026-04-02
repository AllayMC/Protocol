package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to stop a sound playing to the player, such as a playing music disk track or
 * other long-lasting sounds.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class StopSoundPacket implements BedrockPacket {
    /**
     * The name of the sound that should be stopped from playing. If no sound with this name is
     * currently active, the packet is ignored.
     */
    private String soundName;
    /**
     * Specifies if all currently playing sounds should be stopped. When true,
     * {@link #soundName} may be left empty.
     */
    private boolean stoppingAllSound;
    /**
     * Legacy music-stop flag whose exact modern behaviour is still unknown.
     *
     * @since v712
     */
    private boolean stopMusicLegacy;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.STOP_SOUND;
    }

    @Override
    public StopSoundPacket clone() {
        try {
            return (StopSoundPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
