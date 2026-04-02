package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.map.MapPixel;

import java.util.List;

/**
 * Sent by the client to request the server to deliver information of a certain map in the inventory
 * of the player. The server should respond with a ClientBoundMapItemData packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MapInfoRequestPacket implements BedrockPacket {
    /**
     * MapID is the unique identifier that represents the map that is requested over network. It
     * remains consistent across sessions.
     */
    private long uniqueMapId;
    /**
     * A list of pixels the client already knows about, allowing the server to send only the map
     * updates that are still missing client-side.
     *
     * @since v544
     */
    private final List<MapPixel> pixels = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MAP_INFO_REQUEST;
    }

    @Override
    public MapInfoRequestPacket clone() {
        try {
            return (MapInfoRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
