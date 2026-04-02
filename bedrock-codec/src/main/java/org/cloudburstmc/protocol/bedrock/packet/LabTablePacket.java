package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.inventory.LabTableReactionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.LabTableType;

/**
 * Sent by the client to let the server know it started a chemical reaction in Education Edition,
 * and is sent by the server to other clients to show the effects. The packet is only functional if
 * Education features are enabled.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LabTablePacket implements BedrockPacket {
    /**
     * The lab table action being performed. Clients typically send
     * {@link LabTableType#START_COMBINE} when starting a reaction, while servers broadcast the
     * resulting reaction state.
     */
    private LabTableType type;
    /**
     * The position at which the lab table used was located.
     */
    private Vector3i position;
    /**
     * The type of the reaction that took place as a result of the items put into the lab table. The
     * reaction type can be either that of an item or a particle, depending on whatever the result
     * was of the reaction.
     */
    private LabTableReactionType reactionType;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LAB_TABLE;
    }

    @Override
    public LabTablePacket clone() {
        try {
            return (LabTablePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
