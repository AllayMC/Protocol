package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to apply an effect to the player, for example an effect like poison. It may
 * also be used to modify existing effects, or removing them completely.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MobEffectPacket implements BedrockPacket {
    /**
     * The runtime ID of the entity. The runtime ID is unique for each world session, and entities
     * are generally identified in packets using this runtime ID.
     */
    private long runtimeEntityId;
    /**
     * The operation to perform on the effect: add it, modify the existing instance, or remove it.
     */
    private Event event;
    /**
     * The numeric ID of the effect to add, modify, or remove.
     */
    private int effectId;
    /**
     * The amplifier of the effect. Take note that the amplifier is not the same as the effect's
     * level. The level is usually one higher than the amplifier, and the amplifier can actually be
     * negative to reverse the behaviour effect.
     */
    private int amplifier;
    /**
     * Specifies if viewers of the entity that gets the effect shows particles around it. If set to
     * false, no particles are emitted around the entity.
     */
    private boolean particles;
    /**
     * The duration of the effect in seconds. After the duration has elapsed, the effect will be
     * removed automatically client-side.
     */
    private int duration;
    /**
     * The server tick at which the packet was sent. It is used in relation to
     * CorrectPlayerMovePrediction.
     *
     * @since v662
     */
    private long tick;
    /**
     * Specifies if the effect is ambient. If set to false, it will not get treated as an ambient
     * effect.
     *
     * @since v898
     */
    private boolean ambient;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOB_EFFECT;
    }

    public enum Event {
        NONE,
        ADD,
        MODIFY,
        REMOVE,
    }

    @Override
    public MobEffectPacket clone() {
        try {
            return (MobEffectPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
