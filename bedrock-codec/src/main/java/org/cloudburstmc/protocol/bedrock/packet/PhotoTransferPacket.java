package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.PhotoType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to transfer a photo (image) file to the client. It is typically used to
 * transfer photos so that the client can display it in a portfolio in Education Edition. While
 * previously usable in the default Bedrock Edition, the displaying of photos in books was disabled
 * and the packet now has little use anymore.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PhotoTransferPacket implements BedrockPacket {
    private String name;
    private byte[] data;
    private String bookId;
    /**
     * @since v465
     */
    private PhotoType photoType;
    /**
     * @since v465
     */
    private PhotoType sourceType;
    /**
     * @since v465
     */
    private long ownerId;
    /**
     * @since v465
     */
    private String newPhotoName;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PHOTO_TRANSFER;
    }

    @Override
    public PhotoTransferPacket clone() {
        try {
            return (PhotoTransferPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
