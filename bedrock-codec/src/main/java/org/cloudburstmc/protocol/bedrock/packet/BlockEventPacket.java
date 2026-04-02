package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;

/**
 * Used to trigger Note blocks, Chests and End Gateways
 *
 * <h2>Examples</h2>
 *
 * <h3>Note Block</h3>
 *
 * <blockquote>
 * <p>
 * eventType: (Instrument)
 *
 * <ul>
 *   <li>0 (Piano)
 *   <li>1 (Base Drum)
 *   <li>2 (Sticks)
 *   <li>3 (Drum)
 *   <li>4 (Bass)
 * </ul>
 * <p>
 * data: 0-15
 *
 * </blockquote>
 *
 * <h3>Chest Block</h3>
 *
 * <blockquote>
 * <p>
 * eventType: 1 (Chest open/closed)<br>
 * data: 0 or 1
 *
 * </blockquote>
 *
 * <h3>End Gateway</h3>
 *
 * <blockquote>
 * <p>
 * eventType: 1 (Cool down)<br>
 * data: n/a
 *
 * </blockquote>
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class BlockEventPacket implements BedrockPacket {
    /**
     * The position of the block that the event applies to.
     */
    private Vector3i blockPosition;
    /**
     * The type of block event to trigger. The meaning of {@link #eventData} depends on this value.
     */
    private int eventType;
    /**
     * Event-specific data. For example, chests use {@code 1} when opening and {@code 0} when
     * closing.
     */
    private int eventData;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BLOCK_EVENT;
    }

    @Override
    public BlockEventPacket clone() {
        try {
            return (BlockEventPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
