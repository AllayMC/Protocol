package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ee.AgentActionType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * An Education Edition packet sent from the server to the client to return a response to a
 * previously requested action.
 *
 * @since v503
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AgentActionEventPacket implements BedrockPacket {
    private String requestId;
    private AgentActionType actionType;

    /**
     * @see AgentActionType for type specific JSON
     */
    private String responseJson;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.AGENT_ACTION_EVENT;
    }

    @Override
    public AgentActionEventPacket clone() {
        try {
            return (AgentActionEventPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
