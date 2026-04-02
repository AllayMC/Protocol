package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.annotation.NetEaseOnly;

/**
 * NetEase packet used for Python scripting RPC calls.
 */
@NetEaseOnly
@Data
@EqualsAndHashCode(doNotUseGetters = true)
public class PyRpcPacket implements BedrockPacket {
    private byte[] data;
    private long msgId;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.PY_RPC;
    }

    @Override
    public PyRpcPacket clone() {
        try {
            return (PyRpcPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
