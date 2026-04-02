package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.Ability;

/**
 * Sent by the client to request that the server update or grant a specific ability value. The
 * payload carries either a boolean or float value depending on the selected ability type.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RequestAbilityPacket implements BedrockPacket {
    /**
     * The ability whose value the client wants to update.
     */
    private Ability ability;
    /**
     * The wire type used for the requested value.
     */
    private Ability.Type type;
    /**
     * The requested boolean value when {@link #type} is {@link Ability.Type#BOOLEAN}.
     */
    private boolean boolValue;
    /**
     * The requested float value when {@link #type} is {@link Ability.Type#FLOAT}.
     */
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
