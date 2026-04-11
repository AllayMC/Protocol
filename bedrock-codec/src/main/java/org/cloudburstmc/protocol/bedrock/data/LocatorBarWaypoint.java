package org.cloudburstmc.protocol.bedrock.data;

import lombok.Data;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;

import java.awt.*;

/**
 * Represents a waypoint entry in the locator bar packet.
 */
@Data
public class LocatorBarWaypoint {

    /**
     * A bitmask describing which optional waypoint fields are present.
     */
    private int updateFlag;
    /**
     * Whether the waypoint should be visible on the locator bar.
     */
    private Boolean visible;
    /**
     * The waypoint's world position and dimension.
     */
    private WorldPosition worldPosition;
    /**
     * The waypoint icon texture identifier.
     */
    private Integer textureId;
    /**
     * The waypoint icon texture path.
     *
     * @since v974
     */
    private String texturePath;
    /**
     * The waypoint icon size.
     *
     * @since v974
     */
    private Vector2f iconSize;
    /**
     * The tint colour used for the waypoint icon.
     */
    private Color color;
    /**
     * Whether the client is allowed to control the waypoint position locally.
     */
    private Boolean clientPositionAuthority;
    /**
     * The unique ID of the entity that this waypoint tracks, if any.
     */
    private Long entityUniqueId;

    public record WorldPosition(Vector3f position, int dimension) {
    }
}
