package org.cloudburstmc.protocol.bedrock.data.definitions;

import java.util.UUID;

/**
 * Describes a data-driven dimension registered through {@link
 * org.cloudburstmc.protocol.bedrock.packet.DimensionDataPacket}.
 *
 * @param id            the dimension identifier
 * @param maximumHeight the upper build limit; since v2193 the wire stores its difference from minimumHeight
 * @param minimumHeight the lower build limit
 * @param generatorType the generator variant
 * @param dimensionType the numeric dimension type, since v975
 * @param packId        the owning pack identifier, since v2168
 * @param defaultBiome  the default biome identifier, since v2192
 * @param cloudHeight   the cloud layer height, since v2225
 * @param renderClouds  whether clouds are rendered, since v2225
 * @since v503
 */
public record DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType,
                                  int dimensionType, UUID packId, String defaultBiome,
                                  int cloudHeight, boolean renderClouds) {

    public DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType,
                               int dimensionType, UUID packId, String defaultBiome) {
        this(id, maximumHeight, minimumHeight, generatorType, dimensionType, packId, defaultBiome, 0, false);
    }

    public DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType,
                               int dimensionType, UUID packId) {
        this(id, maximumHeight, minimumHeight, generatorType, dimensionType, packId, null);
    }

    public DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType) {
        this(id, maximumHeight, minimumHeight, generatorType, 0);
    }

    public DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType,
                               int dimensionType) {
        this(id, maximumHeight, minimumHeight, generatorType, dimensionType, new UUID(0, 0));
    }
}
