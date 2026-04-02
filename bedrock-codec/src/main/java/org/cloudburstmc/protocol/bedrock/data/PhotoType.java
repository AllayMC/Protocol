package org.cloudburstmc.protocol.bedrock.data;

/**
 * The type of photo container referenced by {@link org.cloudburstmc.protocol.bedrock.packet.PhotoTransferPacket}.
 */
public enum PhotoType {
    /**
     * A photo stored in the Education Edition portfolio.
     */
    PORTFOLIO,
    /**
     * A standalone photo item.
     */
    PHOTO_ITEM,
    /**
     * A photo associated with a book entry.
     */
    BOOK;

    private static final PhotoType[] VALUES = values();

    public static PhotoType from(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : null;
    }
}
