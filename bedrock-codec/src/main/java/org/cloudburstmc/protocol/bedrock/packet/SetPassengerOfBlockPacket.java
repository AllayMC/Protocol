package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.PassengerOfBlockArguments;

/**
 * Sent when an actor starts or stops riding a block.
 *
 * @since v2225
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetPassengerOfBlockPacket implements BedrockPacket {

    private long entityUniqueId;
    /**
     * The block attachment and pose, or null when the actor stops riding the block.
     */
    @Nullable
    private PassengerOfBlockArguments data;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_PASSENGER_OF_BLOCK;
    }

    @Override
    public SetPassengerOfBlockPacket clone() {
        try {
            return (SetPassengerOfBlockPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
