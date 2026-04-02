package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitions;

/**
 * Sent by the server to describe the biome definitions available for the current world.
 * Depending on the protocol version, the payload is either encoded as legacy NBT or as structured
 * biome definition entries used by newer clients.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class BiomeDefinitionListPacket implements BedrockPacket {
    /**
     * @deprecated As of v800 (1.21.80) the biomes are no longer sent as NBT. Use {@link #biomes}
     * instead.
     */
    @Deprecated
    private NbtMap definitions;
    /**
     * Structured biome definitions used by protocol v800 (1.21.80) and later.
     */
    private BiomeDefinitions biomes;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.BIOME_DEFINITIONS_LIST;
    }

    @Override
    public BiomeDefinitionListPacket clone() {
        try {
            return (BiomeDefinitionListPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
