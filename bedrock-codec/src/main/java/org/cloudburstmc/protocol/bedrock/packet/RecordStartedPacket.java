package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.packet.PacketSignal;

/**
 * Sent to notify the client that a record started playing at a block position.
 *
 * @since v2192
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class RecordStartedPacket implements BedrockPacket {

    private Vector3i blockPos;
    private long serverSoundHandle;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RECORD_STARTED;
    }

    @Override
    public RecordStartedPacket clone() {
        try {
            return (RecordStartedPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
