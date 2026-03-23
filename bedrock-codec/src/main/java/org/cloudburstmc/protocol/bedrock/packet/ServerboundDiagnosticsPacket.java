package org.cloudburstmc.protocol.bedrock.packet;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.MemoryCategoryCounter;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the client to tell the server about the performance diagnostics of the client. It is sent
 * by the client roughly every 500ms or 10 in-game ticks when the "Creator &gt; Enable Client
 * Diagnostics" setting is enabled.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundDiagnosticsPacket implements BedrockPacket {
    private float avgFps;
    private float avgServerSimTickTimeMS;
    private float avgClientSimTickTimeMS;
    private float avgBeginFrameTimeMS;
    private float avgInputTimeMS;
    private float avgRenderTimeMS;
    private float avgEndFrameTimeMS;
    private float avgRemainderTimePercent;
    private float avgUnaccountedTimePercent;

    /**
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
