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
    /**
     * The exact photo file name, including its extension.
     */
    private String name;
    /**
     * The raw bytes of the photo image. Formats such as PNG and JPEG are accepted as long as
     * {@link #name} uses a matching file extension.
     */
    private byte[] data;
    /**
     * The ID of the book that the photo is associated with. If the PhotoName in a book with this ID
     * is set to PhotoName, it will display the photo (provided Education Edition is used). The
     * photo image is downloaded to a sub-folder with this book ID.
     */
    private String bookId;
    /**
     * The type of photo being transferred.
     *
     * @since v465
     */
    private PhotoType photoType;
    /**
     * The original type of the photo before this transfer.
     *
     * @since v465
     */
    private PhotoType sourceType;
    /**
     * The unique entity ID of the photo owner.
     *
     * @since v465
     */
    private long ownerId;
    /**
     * The new name of the photo.
     *
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
