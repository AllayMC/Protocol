package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make any kind of built-in sound heard to a player. It is sent to, for
 * example, play a stepping sound or a shear sound. The packet is also sent by the client, in which
 * case it could be forwarded by the server to the other players online. If possible, the packets
 * from the client should be ignored however, and the server should play them on its own accord.
 *
 * @deprecated since v786
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class LevelSoundEvent2Packet implements BedrockPacket {
    /**
     * The built-in sound event to play.
     */
    private SoundEvent sound;
    /**
     * The position of the sound event. The player will be able to hear the direction of the sound
     * based on what position is sent here.
     */
    private Vector3f position;
    /**
     * A packed integer that some sound types use to provide extra data. An example of this is the
     * note sound, which is composed of a pitch and an instrument type.
     */
    private int extraData;
    /**
     * The string entity type of the entity that emitted the sound, for example
     * 'minecraft:skeleton'. Some sound types use this entity type for additional data.
     */
    private String identifier;
    /**
     * Specifies if the sound should use the baby variant, if the sound supports one.
     */
    private boolean babySound;
    /**
     * DisableRelativeVolume specifies if the sound should be played relatively or not. If set to
     * true, the sound will have full volume, regardless of where the Position is, whereas if set to
     * false, the sound's volume will be based on the distance to Position.
     */
    private boolean relativeVolumeDisabled;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_SOUND_EVENT_2;
    }

    @Override
    public LevelSoundEvent2Packet clone() {
        try {
            return (LevelSoundEvent2Packet) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
