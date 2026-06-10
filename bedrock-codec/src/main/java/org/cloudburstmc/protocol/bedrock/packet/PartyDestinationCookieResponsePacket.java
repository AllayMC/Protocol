package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the client to the server with a party destination cookie response.
 *
 * @since v1001
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PartyDestinationCookieResponsePacket implements BedrockPacket {

    /**
     * The opaque party destination cookie echoed back from {@link SendPartyDestinationCookiePacket}.
     */
    private String cookie;
    /**
     * Whether the client accepted the party destination.
     */
    private boolean accepted;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PARTY_DESTINATION_COOKIE_RESPONSE;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (PartyDestinationCookieResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
