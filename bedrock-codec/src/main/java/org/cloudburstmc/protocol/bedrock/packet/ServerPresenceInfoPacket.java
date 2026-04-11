package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the server to advertise optional presence metadata.
 *
 * @since v974
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerPresenceInfoPacket implements BedrockPacket {

    private PresenceConfiguration presenceConfiguration;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVER_PRESENCE_INFO;
    }

    @Override
    public ServerPresenceInfoPacket clone() {
        try {
            return (ServerPresenceInfoPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Data
    @EqualsAndHashCode(doNotUseGetters = true)
    @ToString(doNotUseGetters = true)
    public static class PresenceConfiguration {
        private String experienceName;
        private String worldName;
    }
}
