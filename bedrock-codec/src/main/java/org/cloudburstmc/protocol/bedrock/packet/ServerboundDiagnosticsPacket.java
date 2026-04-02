package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.MemoryCategoryCounter;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.ArrayList;
import java.util.List;

/**
 * Sent by the client to tell the server about the performance diagnostics of the client. It is sent
 * by the client roughly every 500ms or 10 in-game ticks when the "Creator &gt; Enable Client
 * Diagnostics" setting is enabled.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundDiagnosticsPacket implements BedrockPacket {
    /**
     * The average number of frames per second rendered by the client.
     */
    private float avgFps;
    /**
     * The average time, in milliseconds, that the server spends simulating a single tick.
     */
    private float avgServerSimTickTimeMS;
    /**
     * The average time, in milliseconds, that the client spends simulating a single tick.
     */
    private float avgClientSimTickTimeMS;
    /**
     * The average time, in milliseconds, that the client spends beginning a frame.
     */
    private float avgBeginFrameTimeMS;
    /**
     * The average time, in milliseconds, that the client spends processing input.
     */
    private float avgInputTimeMS;
    /**
     * The average time, in milliseconds, that the client spends rendering a frame.
     */
    private float avgRenderTimeMS;
    /**
     * The average time, in milliseconds, that the client spends ending a frame.
     */
    private float avgEndFrameTimeMS;
    /**
     * The average percentage of frame time spent on work that is not otherwise accounted for.
     */
    private float avgRemainderTimePercent;
    /**
     * The average percentage of frame time that the client reports as fully unaccounted.
     */
    private float avgUnaccountedTimePercent;
    /**
     * A list of client memory counters grouped by category.
     *
     * @since v924
     */
    private final List<MemoryCategoryCounter> memoryCategoryValues = new ArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_DIAGNOSTICS;
    }

    @Override
    public ServerboundDiagnosticsPacket clone() {
        try {
            return (ServerboundDiagnosticsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
