package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Used to trigger an entity animation on the specified runtime IDs to the client that receives it.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AnimateEntityPacket implements BedrockPacket {
    /**
     * The animation identifier to play on the entities specified in {@link #runtimeEntityIds}.
     */
    private String animation;
    /**
     * The entity state to move to when the animation has finished playing.
     */
    private String nextState;
    /**
     * Molang expression evaluated by the client to determine when the animation should stop.
     */
    private String stopExpression;
    /**
     * Name of the animation controller that owns or drives the animation transition.
     */
    private String controller;
    /**
     * Time taken to blend out of the specified animation.
     */
    private float blendOutTime;
    /**
     * Entity runtime IDs to run the animation on when sent to the client.
     */
    private final LongList runtimeEntityIds = new LongArrayList();
    /**
     * The Molang expression version to use when interpreting {@link #stopExpression}.
     *
     * @since v465
     */
    private int stopExpressionVersion;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ANIMATE_ENTITY;
    }

    @Override
    public AnimateEntityPacket clone() {
        try {
            return (AnimateEntityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
