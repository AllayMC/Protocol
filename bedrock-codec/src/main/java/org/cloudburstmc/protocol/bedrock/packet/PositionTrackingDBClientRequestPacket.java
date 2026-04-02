package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * A packet sent by the client to request the position and dimension of a 'tracking ID'. These IDs
 * are tracked in a database by the server. In 1.16, this is used for lodestones. The client will
 * send this request to find the position a lodestone compass needs to point to. If found, it will
 * point to the lodestone. If not, it will start spinning around. A
 * PositionTrackingDBServerBroadcast packet should be sent in response to this packet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PositionTrackingDBClientRequestPacket implements BedrockPacket {
    /**
     * The action to perform on the position tracking database.
     */
    private Action action;
    /**
     * A unique ID used to identify the request. The server responds with a
     * PositionTrackingDBServerBroadcast packet holding the same ID, so that the client can find out
     * what that packet was in response to.
     */
    private int trackingId;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.POSITION_TRACKING_DB_CLIENT_REQUEST;
    }

    public enum Action {
        /**
         * Query the tracked position associated with {@link PositionTrackingDBClientRequestPacket#trackingId}.
         */
        QUERY
    }

    @Override
    public PositionTrackingDBClientRequestPacket clone() {
        try {
            return (PositionTrackingDBClientRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
