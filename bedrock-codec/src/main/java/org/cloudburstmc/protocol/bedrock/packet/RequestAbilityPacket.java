package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * A packet sent by the client to the server to request permission for a specific ability from the
 * server. These abilities are defined above.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RequestAbilityPacket implements BedrockPacket {
    private Ability ability;
    private Ability.Type type;
    private boolean boolValue;
    private float floatValue;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.REQUEST_ABILITY;
    }

    @Override
    public RequestAbilityPacket clone() {
        try {
            return (RequestAbilityPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
