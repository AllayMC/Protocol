package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.MovementEffectType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client to update specific movement effects to allow the client to
 * predict its movement. For example, fireworks used during gliding will send this packet to tell
 * the client the exact duration of the boost.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MovementEffectPacket implements BedrockPacket {
    /**
     * The runtime ID of the entity. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long entityRuntimeId;
    /**
     * The type of movement effect being updated, such as the gliding firework boost.
     */
    private MovementEffectType effectType;
    /**
     * The duration of the effect, measured in ticks.
     */
    private int duration;
    /**
     * The server tick at which the packet was sent. It is used in relation to
     * CorrectPlayerMovePrediction.
     */
    private long tick;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOVEMENT_EFFECT;
    }

    @Override
    public MovementEffectPacket clone() {
        try {
            return (MovementEffectPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
