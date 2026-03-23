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
    private int page;

    /**
     * @since v354
     */
    private int totalPages;

    private Vector3i blockPosition;

    /**
     * @deprecated since v662
     */
    @Deprecated private boolean droppingBook;

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
