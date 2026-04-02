package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to update the server on which page was opened in a book on a lectern, or if
 * the book should be removed from it.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LecternUpdatePacket implements BedrockPacket {
    /**
     * The page number in the book that was opened by the player on the lectern.
     */
    private int page;
    /**
     * The position of the lectern that was updated. If there is no lectern at this position, the
     * packet should be ignored.
     */
    private Vector3i blockPosition;
    /**
     * Specifies if the client requested the book to be removed from the lectern.
     *
     * @deprecated since v662
     */
    @Deprecated
    private boolean droppingBook;
    /**
     * The total number of pages in the book currently on the lectern.
     *
     * @since v354
     */
    private int totalPages;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LECTERN_UPDATE;
    }

    @Override
    public LecternUpdatePacket clone() {
        try {
            return (LecternUpdatePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
