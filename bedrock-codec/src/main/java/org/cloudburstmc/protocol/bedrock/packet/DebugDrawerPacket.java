package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.debugshape.DebugShape;

import java.util.List;

/**
 * Sent by the server to instruct the client to render one or more debug shapes. Each packet
 * replaces the previously rendered debug-drawer state.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class DebugDrawerPacket implements BedrockPacket {

    /**
     * The shapes to render client-side. Omitting a previously sent shape removes it on the next
     * update.
     */
    private final List<DebugShape> shapes = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.DEBUG_DRAWER;
    }

    @Override
    public BedrockPacket clone() {
        try {
            return (DebugDrawerPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
