package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * An Education Edition packet sent from the server to the client to make an agent perform an
 * animation.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AgentAnimationPacket implements BedrockPacket {
    /**
     * The ID of the animation that the agent should perform. As of its implementation, there are no
     * IDs that can be used in the regular client.
     */
    private byte animation;
    /**
     * The runtime ID of the agent entity that should perform the animation.
     */
    private long runtimeEntityId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.AGENT_ANIMATION;
    }

    @Override
    public AgentAnimationPacket clone() {
        try {
            return (AgentAnimationPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
