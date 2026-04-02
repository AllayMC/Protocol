package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.ClientboundDebugRendererType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to add or clear client-side debug markers.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundDebugRendererPacket implements BedrockPacket {
    /**
     * The debug renderer action to perform.
     */
    private ClientboundDebugRendererType debugMarkerType;
    /**
     * The label shown for an added debug cube.
     */
    private String markerText;
    /**
     * The world position of an added debug cube.
     */
    private Vector3f markerPosition;
    /**
     * The red component of an added debug cube's color, in the range {@code 0.0-1.0}.
     */
    private float markerColorRed;
    /**
     * The green component of an added debug cube's color, in the range {@code 0.0-1.0}.
     */
    private float markerColorGreen;
    /**
     * The blue component of an added debug cube's color, in the range {@code 0.0-1.0}.
     */
    private float markerColorBlue;
    /**
     * The alpha component of an added debug cube's color, in the range {@code 0.0-1.0}.
     */
    private float markerColorAlpha;
    /**
     * How long an added debug cube remains visible, in milliseconds.
     */
    private long markerDuration;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_DEBUG_RENDERER;
    }

    @Override
    public ClientboundDebugRendererPacket clone() {
        try {
            return (ClientboundDebugRendererPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
