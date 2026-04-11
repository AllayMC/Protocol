package org.cloudburstmc.protocol.bedrock.data.definitions;

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
 */
public record DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType,
                                  int dimensionType) {

    public DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType) {
        this(id, maximumHeight, minimumHeight, generatorType, 0);
    }
}
