package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;

/**
 * Sent by the server to play a sound to the client. Some of the sounds may only be started using
 * this packet and must be stopped using the StopSound packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PlaySoundPacket implements BedrockPacket {
    /**
     * The name of the sound to play.
     */
    private String sound;
    /**
     * The position at which the sound was played. Some sounds do not depend on a position, which
     * will then ignore it, but most of them will play with the direction based on the position
     * compared to the player's position.
     */
    private Vector3f position;
    /**
     * The relative volume of the sound to play. It will be less loud for the player if it is
     * farther away from the position of the sound.
     */
    private float volume;
    /**
     * The pitch of the sound to play. Some sounds completely ignore this field, whereas others use
     * it to specify the pitch as the field is intended.
     */
    private float pitch;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PLAY_SOUND;
    }

    @Override
    public PlaySoundPacket clone() {
        try {
            return (PlaySoundPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
