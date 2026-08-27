package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.FurnaceOptions;
import org.cloudburstmc.protocol.bedrock.packet.PacketSignal;

/**
 * @since v2192
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SetPlayerFurnaceOptionsPacket implements BedrockPacket {

    private FurnaceOptions options;
    private FurnaceType type;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SET_PLAYER_FURNACE_OPTIONS;
    }

    @Override
    public SetPlayerFurnaceOptionsPacket clone() {
        try {
            return (SetPlayerFurnaceOptionsPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum FurnaceType {
        NONE,
        FURNACE,
        BLAST_FURNACE,
        SMOKER
    }
}
