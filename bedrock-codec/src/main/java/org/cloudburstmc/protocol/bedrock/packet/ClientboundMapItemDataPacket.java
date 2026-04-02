package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.MapDecoration;
import org.cloudburstmc.protocol.bedrock.data.MapTrackedObject;

import java.util.List;

/**
 * Sent by the server to the client to update the data of a map shown to the client. It is sent with
 * a combination of flags that specify what data is updated. The ClientBoundMapItemData packet may
 * be used to update specific parts of the map only. It is not required to send the entire map each
 * time when updating one part.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundMapItemDataPacket implements BedrockPacket {
    /**
     * Runtime tracking ids for entities associated with this map update.
     */
    private final LongList trackedEntityIds = new LongArrayList();
    /**
     * A list of tracked objects on the map, which may either be entities or blocks. The client
     * makes sure these tracked objects are actually tracked. (position updated etc.).
     */
    private final List<MapTrackedObject> trackedObjects = new ObjectArrayList<>();
    /**
     * A list of fixed decorations located on the map. The decorations will not change client-side,
     * unless the server updates them.
     */
    private final List<MapDecoration> decorations = new ObjectArrayList<>();
    /**
     * MapID is the unique identifier that represents the map that is updated over network. It
     * remains consistent across sessions.
     */
    private long uniqueMapId;
    /**
     * The dimension of the map being updated, for example overworld, nether, or end.
     */
    private int dimensionId;
    /**
     * The scale of the map as it is shown in-game. It is written when any of the MapUpdateFlags are
     * set to the UpdateFlags field.
     */
    private int scale;
    /**
     * The height of the texture area that was updated. The height may be a subset of the total
     * height of the map.
     */
    private int height;
    /**
     * The width of the texture area that was updated. The width may be a subset of the total width
     * of the map.
     */
    private int width;
    /**
     * The X offset in pixels at which the updated texture area starts. From this X, the updated
     * texture will extend exactly Width pixels to the right.
     */
    private int xOffset;
    /**
     * The Y offset in pixels at which the updated texture area starts. From this Y, the updated
     * texture will extend exactly Height pixels up.
     */
    private int yOffset;
    /**
     * Pixel colours for the updated map texture region. Indexed as {@code colors[y * height + x]}.
     */
    private int[] colors;
    /**
     * Whether locked.
     *
     * @since v354
     */
    private boolean locked;
    /**
     * The center position of the map being updated.
     *
     * @since v544
     */
    private Vector3i origin;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_MAP_ITEM_DATA;
    }

    @Override
    public ClientboundMapItemDataPacket clone() {
        try {
            return (ClientboundMapItemDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
