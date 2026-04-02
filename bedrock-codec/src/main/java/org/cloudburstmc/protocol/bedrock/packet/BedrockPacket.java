package org.cloudburstmc.protocol.bedrock.packet;

/**
 * Base contract for packets encoded on the Bedrock protocol.
 */
public interface BedrockPacket extends Cloneable {

    /**
     * Dispatches this packet to the matching overload on {@code handler}.
     *
     * @param handler the handler receiving this packet
     * @return the signal returned by the handler
     */
    PacketSignal handle(BedrockPacketHandler handler);

    /**
     * Returns the logical Bedrock packet type represented by this instance.
     *
     * @return the packet type constant for this packet
     */
    BedrockPacketType getPacketType();

    /**
     * Creates a shallow copy of this packet using {@link Object#clone()}.
     * Implementations that also implement {@link io.netty.util.ReferenceCounted} may instead throw
     * {@link UnsupportedOperationException}.
     *
     * @return a cloned packet instance
     */
    BedrockPacket clone();
}
