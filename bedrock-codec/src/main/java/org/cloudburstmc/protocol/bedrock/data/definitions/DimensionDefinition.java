package org.cloudburstmc.protocol.bedrock.data.definitions;

import java.util.UUID;

/**
 * Describes a data-driven dimension that may be registered through {@link
 * org.cloudburstmc.protocol.bedrock.packet.DimensionDataPacket}. The definition includes the
 * dimension identifier, build-height range, and generator variant.
 *
 * @param id            the dimension identifier
 * @param maximumHeight the upper build limit of the dimension
 * @param minimumHeight the lower build limit of the dimension
 * @param generatorType the generator variant used for the dimension
 * @param dimensionType the numeric dimension type sent by modern codecs
 * @param packId        the owning pack identifier sent by v2168+
 */
public record DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType,
                                  int dimensionType, UUID packId) {

    public DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType) {
        this(id, maximumHeight, minimumHeight, generatorType, 0);
    }

    public DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType,
                               int dimensionType) {
        this(id, maximumHeight, minimumHeight, generatorType, dimensionType, new UUID(0, 0));
    }
}
