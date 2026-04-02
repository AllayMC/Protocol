package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.StoreOfferRedirectType;

/**
 * Sent by the server to show a Marketplace store offer to a player. It opens a window client-side
 * that displays the item. The ShowStoreOffer packet only works on the partnered servers: Servers
 * that are not partnered will not have a store buttons show up in the in-game pause menu and will,
 * as a result, not be able to open store offers on the client side. Sending the packet does
 * therefore not work when using a proxy that is not connected to with the domain of one of the
 * partnered servers.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ShowStoreOfferPacket implements BedrockPacket {
    /**
     * A UUID that identifies the offer for which a window should be opened.
     */
    private String offerId;
    /**
     * Legacy marketplace flag retained for compatibility with older protocol versions.
     *
     * @since v630 deprecated
     */
    private boolean shownToAll;
    /**
     * The kind of store page that should be opened for this offer.
     *
     * @since v630
     */
    private StoreOfferRedirectType redirectType;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SHOW_STORE_OFFER;
    }

    @Override
    public ShowStoreOfferPacket clone() {
        try {
            return (ShowStoreOfferPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
