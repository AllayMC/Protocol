package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCounted;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;

/**
 * Represents a Bedrock packet whose concrete type is not implemented by this codec. The packet ID
 * and raw payload are preserved so the packet can still be inspected or forwarded without a
 * dedicated serializer.
 *
 * <p>The payload is stored as a retained {@link ByteBuf}, so normal Netty reference-counting rules
 * apply when this packet is passed around.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
public final class UnknownPacket
        implements BedrockPacket, BedrockPacketSerializer<UnknownPacket>, ReferenceCounted {
    /**
     * The numeric ID read from the packet header.
     */
    private int packetId;
    /**
     * The raw packet body exactly as it appeared on the wire.
     */
    private ByteBuf payload;

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UnknownPacket packet) {
        buffer.writeBytes(packet.payload, packet.payload.readerIndex(), packet.payload.readableBytes());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UnknownPacket packet) {
        packet.payload = buffer.readRetainedSlice(buffer.readableBytes());
    }

    @Override
    public String toString() {
        return "UNKNOWN - "
               + getPacketId()
               + " - Hex: "
               + (payload == null || payload.refCnt() == 0 ? "null" : ByteBufUtil.hexDump(payload));
    }

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return PacketSignal.UNHANDLED;
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UNKNOWN;
    }

    @Override
    public int refCnt() {
        return this.payload == null ? 0 : payload.refCnt();
    }

    @Override
    public UnknownPacket retain() {
        if (this.payload != null) {
            this.payload.retain();
        }
        return this;
    }

    @Override
    public UnknownPacket retain(int increment) {
        if (this.payload != null) {
            this.payload.retain(increment);
        }
        return this;
    }

    @Override
    public UnknownPacket touch() {
        if (this.payload != null) {
            this.payload.touch();
        }
        return this;
    }

    @Override
    public UnknownPacket touch(Object hint) {
        if (this.payload != null) {
            this.payload.touch(hint);
        }
        return this;
    }

    @Override
    public boolean release() {
        return this.payload == null || this.payload.release();
    }

    @Override
    public boolean release(int decrement) {
        return this.payload == null || this.payload.release(decrement);
    }

    @Override
    public UnknownPacket clone() {
        throw new UnsupportedOperationException("Can not clone reference counted packet");
    }
}
