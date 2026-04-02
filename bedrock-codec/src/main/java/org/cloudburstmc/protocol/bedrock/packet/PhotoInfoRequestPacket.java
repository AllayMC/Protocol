package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to request photo information from the server.
 *
 * @deprecated This packet was deprecated in 1.19.80.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Deprecated
public class PhotoInfoRequestPacket implements BedrockPacket {
    /**
     * The unique ID of the photo whose metadata is being requested.
     */
    private long photoId;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PHOTO_INFO_REQUEST;
    }

    @Override
    public PhotoInfoRequestPacket clone() {
        try {
            return (PhotoInfoRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
