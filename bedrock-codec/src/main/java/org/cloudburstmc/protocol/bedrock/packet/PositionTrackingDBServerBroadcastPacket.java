package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server in response to the PositionTrackingDBClientRequest packet. This packet is, as
 * of 1.16, currently only used for lodestones. The server maintains a database with tracking IDs
 * and their position and dimension. The client will request these tracking IDs, (NBT tag set on the
 * lodestone compass with the tracking ID?) and the server will respond with the status of those
 * tracking IDs. What is actually done with the data sent depends on what the client chooses to do
 * with it. For the lodestone compass, it is used to make the compass point towards lodestones and
 * to make it spin if the lodestone at a position is no longer there.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PositionTrackingDBServerBroadcastPacket implements BedrockPacket {
    private Action action;
    private int trackingId;
    private NbtMap tag;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.POSITION_TRACKING_DB_SERVER_BROADCAST;
    }

    public enum Action {
        UPDATE,
        DESTROY,
        NOT_FOUND
    }

    @Override
    public PositionTrackingDBServerBroadcastPacket clone() {
        try {
            return (PositionTrackingDBServerBroadcastPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
