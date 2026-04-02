package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent by the server to let the client know the name of the structure feature that the player is
 * currently occupying.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CurrentStructureFeaturePacket implements BedrockPacket {
    /**
     * The identifier of the structure feature the player is currently occupying. Empty when the
     * player is not inside any tracked structure feature.
     */
    private String currentStructureFeature;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CURRENT_STRUCTURE_FEATURE;
    }

    @Override
    public CurrentStructureFeaturePacket clone() {
        try {
            return (CurrentStructureFeaturePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
