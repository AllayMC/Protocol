package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to the client to make a painting entity show up. It is one of the few entities
 * that cannot be sent using the {@link AddEntityPacket}.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = true)
@ToString(doNotUseGetters = true)
public class AddPaintingPacket extends AddHangingEntityPacket {
    private String motive;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ADD_PAINTING;
    }

    @Override
    public AddPaintingPacket clone() {
        return (AddPaintingPacket) super.clone();
    }
}
