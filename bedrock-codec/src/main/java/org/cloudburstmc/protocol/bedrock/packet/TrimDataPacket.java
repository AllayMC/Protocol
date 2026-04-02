package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.TrimMaterial;
import org.cloudburstmc.protocol.bedrock.data.TrimPattern;

import java.util.List;

/**
 * Sent by the server during the join sequence to advertise the armor trim patterns and materials
 * available to the client.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TrimDataPacket implements BedrockPacket {
    /**
     * The trim patterns the client may use. Their visual presentation is defined by the active
     * resource packs.
     */
    private final List<TrimPattern> patterns = new ObjectArrayList<>();
    /**
     * The trim materials the client may use for recolouring an armor trim.
     */
    private final List<TrimMaterial> materials = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TRIM_DATA;
    }

    @Override
    public TrimDataPacket clone() {
        try {
            return (TrimDataPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
