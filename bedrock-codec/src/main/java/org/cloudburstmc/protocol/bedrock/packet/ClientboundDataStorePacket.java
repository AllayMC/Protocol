package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.datastore.DataStoreAction;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.ArrayList;
import java.util.List;

/**
 * Sent by the server to apply data store updates on the client.
 *
 * @since v898
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundDataStorePacket implements BedrockPacket {

    /**
     * An array of data store changes. Each entry has its own change type discriminator.
     */
    private List<DataStoreAction> updates = new ArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_DATA_STORE;
    }

    @Override
    public ClientboundDataStorePacket clone() {
        try {
            return (ClientboundDataStorePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
