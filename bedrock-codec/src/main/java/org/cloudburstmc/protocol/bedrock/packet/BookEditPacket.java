package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sent by the client when it edits a writable book.
 * The packet is emitted for each completed edit action rather than only when the book screen
 * closes.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class BookEditPacket implements BedrockPacket {
    /**
     * Edit action represented by this packet.
     */
    private Action action;
    /**
     * Inventory slot containing the edited book.
     */
    private int inventorySlot;
    /**
     * Primary page index for the action. Used by all actions except {@link Action#SIGN_BOOK}; for
     * {@link Action#SWAP_PAGES} it is one of the swapped pages.
     */
    private int pageNumber;
    /**
     * Secondary page index used only by {@link Action#SWAP_PAGES}.
     */
    private int secondaryPageNumber;
    /**
     * Page text used by {@link Action#ADD_PAGE} and {@link Action#REPLACE_PAGE}.
     */
    private String text;
    /**
     * Education Edition photo name used by {@link Action#ADD_PAGE} and
     * {@link Action#REPLACE_PAGE}.
     */
    private String photoName;
    /**
     * Book title supplied when {@link Action#SIGN_BOOK} is used.
     */
    private String title;
    /**
     * Author string supplied when {@link Action#SIGN_BOOK} is used.
     * This is client-provided text and does not necessarily match an actual player name.
     */
    private String author;
    /**
     * XUID supplied when {@link Action#SIGN_BOOK} is used.
     */
    private String xuid;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BOOK_EDIT;
    }

    /**
     * Book edit actions encoded by {@link BookEditPacket}.
     */
    public enum Action {
        /**
         * Replaces the content of an existing page.
         */
        REPLACE_PAGE,
        /**
         * Adds a new page.
         */
        ADD_PAGE,
        /**
         * Deletes an existing page.
         */
        DELETE_PAGE,
        /**
         * Swaps two existing pages.
         */
        SWAP_PAGES,
        /**
         * Finalizes the book title and author to sign the book.
         */
        SIGN_BOOK
    }

    @Override
    public BookEditPacket clone() {
        try {
            return (BookEditPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
