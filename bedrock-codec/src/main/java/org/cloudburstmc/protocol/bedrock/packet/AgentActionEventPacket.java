package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.ee.AgentActionType;

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
    /**
     * The request identifier echoed back from the originating Education Edition agent action.
     */
    private String requestId;
    /**
     * The Education Edition agent action that produced this response payload.
     */
    private AgentActionType actionType;
    /**
     * A JSON payload describing the action result. The exact structure depends on {@link
     * #actionType}.
     *
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
