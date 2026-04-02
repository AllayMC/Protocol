package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to make a certain event in the level occur. It ranges from particles, to
 * sounds, and other events such as starting rain and block breaking.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LevelEventPacket implements BedrockPacket {
    /**
     * The level event to trigger.
     */
    private LevelEventType type;
    /**
     * The position of the level event. Practically every event requires this Vec3 set for it, as
     * particles, sounds and block editing relies on it.
     */
    private Vector3f position;
    /**
     * Additional event data whose meaning depends on {@link #type}.
     */
    private int data;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_EVENT;
    }

    @Override
    public LevelEventPacket clone() {
        try {
            return (LevelEventPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
