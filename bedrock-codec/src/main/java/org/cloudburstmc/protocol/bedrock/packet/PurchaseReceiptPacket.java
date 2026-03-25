package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;
import java.util.List;

/**
 * Sent by the client to the server to notify the server it purchased an item from the Marketplace
 * store that was offered by the server. The packet is only used for partnered servers.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PurchaseReceiptPacket implements BedrockPacket {
    private final List<String> receipts = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PURCHASE_RECEIPT;
    }

    @Override
    public PurchaseReceiptPacket clone() {
        try {
            return (PurchaseReceiptPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
