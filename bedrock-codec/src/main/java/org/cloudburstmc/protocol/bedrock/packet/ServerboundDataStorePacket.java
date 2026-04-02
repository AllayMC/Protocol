package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.datastore.DataStoreUpdate;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to apply a single update to a server-managed data store.
 *
 * @since v898
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundDataStorePacket implements BedrockPacket {

    /**
     * The data store update to apply.
     */
    private DataStoreUpdate update = new DataStoreUpdate();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_DATA_STORE;
    }

    @Override
    public ServerboundDataStorePacket clone() {
        try {
            return (ServerboundDataStorePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
